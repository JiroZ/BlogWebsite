package com.bloggie.blogservice.service

import com.bloggie.blogservice.authority.Authorities
import com.bloggie.blogservice.authority.BlogUserAuthority
import com.bloggie.blogservice.dto.Messages.*
import com.bloggie.blogservice.dto.blog.Blog
import com.bloggie.blogservice.dto.blog.BlogIndex
import com.bloggie.blogservice.entities.BlogAccessStatus
import com.bloggie.blogservice.exceptions.BlogException
import com.bloggie.blogservice.exceptions.UserException
import com.bloggie.blogservice.repository.BlogIndexRepository
import com.bloggie.blogservice.repository.BlogRepository
import com.bloggie.blogservice.repository.CommentRepository
import com.bloggie.blogservice.service.contracts.BlogService
import com.bloggie.blogservice.service.contracts.PublicProfileService
import com.bloggie.blogservice.utils.Routes
import org.bson.types.ObjectId
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.util.*


@Service
class DefaultBlogService(
    val blogRepository: BlogRepository,
    val publicProfileService: PublicProfileService,
    val blogIndexRepository: BlogIndexRepository,
    val webClient: WebClient.Builder,
    val requestService: RequestService,
    val commentRepository: CommentRepository
) : BlogService {
    @Throws(UserException::class)
    override fun createBlog(blogRequestMessage: BlogCreateRequestMessage): BlogCreationMessage {
        val blogUser = requestService.getUserByUsername(blogRequestMessage.userName)

        val id = ObjectId().toString()
        val blog = Blog(
            id,
            blogRequestMessage.blogTitle,
            blogRequestMessage.data,
            Date(System.currentTimeMillis()),
            publicProfileService.createPublicProfileByUsername(blogUser.userName),
            blogRequestMessage.accessStatus,
            blogRequestMessage.blogCategory,
            0,
            mutableListOf(),
            mutableListOf()
        )
        val blogIndex = BlogIndex(
            id,
            blogRequestMessage.blogTitle,
            blogRequestMessage.userName,
            blogRequestMessage.accessStatus,
            0
        )
        blogRepository.insert(blog)
        blogIndexRepository.insert(blogIndex)

        return BlogCreationMessage(true, id, "Blog Created Successfully")
    }

    @Throws(BlogException::class, UserException::class)
    override fun deleteBlog(blogCallMessage: BlogCallMessage): BlogDeletionMessage {
        val principal: UserDetails
        var username = ""
        var authorities = HashSet<BlogUserAuthority>()
        if (requestService.userAuthentication != null) {
            principal = requestService.userAuthentication.principal as UserDetails
            username = principal.username
            authorities = principal.authorities as HashSet<BlogUserAuthority>
        }

        try {
            val savedBlog = getBlogById(blogCallMessage.blogId)
            val savedBlogIndex = getBlogIndexById(blogCallMessage.blogId)
            if (username == savedBlog.owner.userName || authorities.contains(BlogUserAuthority(Authorities.ROLE_ADMIN.toString()))) {
                blogRepository.delete(savedBlog)
                blogIndexRepository.delete(savedBlogIndex)

                webClient.build() //  userService.deleteBlogFromUser(blogCallMessage.userName, savedBlog)
                    .delete()
                    .uri(Routes.USER_SERVICE + "user/blog" + savedBlogIndex.blogId)
                    .retrieve()
                    .bodyToMono(BlogUpdateRequest::class.java)
                    .block()

                return BlogDeletionMessage(true, "Blog ${savedBlog.blogTitle} deleted successfully")
            } else {
                throw UserException("Invalid Access")
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            throw BlogException("An error occurred while deleting blog")
        }
    }

    @Throws(BlogException::class)
    override fun getBlogIndexById(id: String): BlogIndex {
        try {
            return blogIndexRepository.findById(id).get()
        } catch (e: IllegalArgumentException) {
            throw UserException("Blog Index not found with id: $id")
        }
    }

    @Throws(BlogException::class)
    override fun getBlogById(id: String): Blog {
        val principal: UserDetails
        var username = ""
        if (requestService.userAuthentication != null) {
            principal = requestService.userAuthentication.principal as UserDetails
            username = principal.username
        }

        try {
            val blog = blogRepository.findById(id).get()
            if (requestService.isUserAuthenticated && blog.blogAccessStatus == BlogAccessStatus.PRIVATE) {
                if (blog.owner.userName == username || blog.sharedWith.contains(
                        publicProfileService.createPublicProfileByUsername(
                            username
                        )
                    )
                ) {
                    return blog
                } else {
                    throw BlogException("You can't access this blog")
                }
            } else if (blog.blogAccessStatus == BlogAccessStatus.PUBLIC) {
                return blog
            } else {
                return blog
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            throw BlogException("Blog not found with Id: $id")
        }
    }

    fun getAllBlogs(): MutableList<Blog> {
        return blogRepository.findAll()
    }

    fun getAllBlogIndexes(): MutableList<BlogIndex> {
        return blogIndexRepository.findAll()
    }
}