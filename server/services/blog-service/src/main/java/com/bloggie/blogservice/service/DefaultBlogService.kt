package com.bloggie.blogservice.service

import com.bloggie.blogservice.authority.Authorities
import com.bloggie.blogservice.dto.Messages.*
import com.bloggie.blogservice.dto.blog.Blog
import com.bloggie.blogservice.dto.blog.BlogIndex
import com.bloggie.blogservice.exceptions.BlogException
import com.bloggie.blogservice.exceptions.UserException
import com.bloggie.blogservice.repository.BlogIndexRepository
import com.bloggie.blogservice.repository.BlogRepository
import com.bloggie.blogservice.service.contracts.BlogService
import com.bloggie.blogservice.service.contracts.PublicProfileService
import org.bson.types.ObjectId
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*


@Service
class DefaultBlogService(
    val blogRepository: BlogRepository
    val publicProfileService: PublicProfileService,
    val blogIndexRepository: BlogIndexRepository
) : BlogService {

    @Throws(UserException::class)
    override fun createBlog(blogRequestMessage: BlogCreateRequestMessage): BlogCreationMessage {
        val blogUser = userService.getBlogUserByUsername(blogRequestMessage.userName)
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

        userService.updateUserBlogByUserName(blogUser.userName, blog)

        return BlogCreationMessage(true, id, "Blog Created Successfully")
    }

    @Throws(BlogException::class, UserException::class)
    override fun deleteBlog(blogCallMessage: BlogCallMessage): BlogDeletionMessage {
        val principal = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val username = principal.username
        val authorities = principal.authorities as Set<*>

        try {
            val savedBlog = getBlogById(blogCallMessage.blogId)
            val savedBlogIndex = getBlogIndexById(blogCallMessage.blogId)
            if (username == savedBlog.owner.userName || authorities.contains(SimpleGrantedAuthority(Authorities.ROLE_ADMIN.toString()))) {
                blogRepository.delete(savedBlog)
                blogIndexRepository.delete(savedBlogIndex)
                userService.deleteBlogFromUser(blogCallMessage.userName, savedBlog)

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
        try {
            return blogRepository.findById(id).get()
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