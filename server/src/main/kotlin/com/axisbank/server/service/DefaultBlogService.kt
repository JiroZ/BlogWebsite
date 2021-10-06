package com.axisbank.server.service

import com.axisbank.server.dto.Messages.*
import com.axisbank.server.dto.blog.Blog
import com.axisbank.server.contracts.BlogService
import com.axisbank.server.dto.user.BlogUser
import com.axisbank.server.entities.BlogAccessStatus
import com.axisbank.server.entities.BlogCategory
import com.axisbank.server.exceptions.BlogException
import com.axisbank.server.exceptions.UserException
import com.axisbank.server.repository.BlogRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

@Service
class DefaultBlogService(
    val blogRepository: BlogRepository,
    val userDetailService: DefaultUserDetailService
) : BlogService {
    @Throws(UserException::class)
    override fun createBlog(blogRequestMessage: BlogCreateRequestMessage): BlogCreationMessage {

        val blogUser = userDetailService.getBlogUserByUsername(blogRequestMessage.userName)
        val blog = Blog(
            ObjectId(),
            blogRequestMessage.blogTitle,
            blogRequestMessage.data,
            Date(System.currentTimeMillis()),
            blogUser,
            blogRequestMessage.accessStatus,
            blogRequestMessage.blogCategory,
            0,
            ArrayList()
        )
        blogRepository.insert(blog)
        userDetailService.updateUserBlogByUserName(blogUser.userName, blog)

        return BlogCreationMessage(true, "Blog Created Successfully")
    }

    @Throws(BlogException::class, UserException::class)
    override fun updateBlog(blogRequestMessage: BlogRequestMessage): BlogUpdateMessage {
        try {
            val savedBlog = getBlogById(blogRequestMessage.id)
            val newBlog = Blog(
                blogRequestMessage.id,
                blogRequestMessage.blogTitle,
                blogRequestMessage.data,
                Date(System.currentTimeMillis()),
                savedBlog.owner,
                blogRequestMessage.accessStatus,
                blogRequestMessage.blogCategory,
                savedBlog.views,
                blogRequestMessage.comments
            )
            blogRepository.save(newBlog)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            throw BlogException("A error occurred while updating blog")
        }
        return BlogUpdateMessage(true, "Blog ${blogRequestMessage.blogTitle} updated successfully")
    }

    @Throws(BlogException::class, UserException::class)
    override fun deleteBlog(blogRequestMessage: BlogRequestMessage): BlogDeletionMessage {
        try {
            val savedBlog = getBlogById(blogRequestMessage.id)
            blogRepository.delete(savedBlog)
            userDetailService.deleteBlogFromUser(blogRequestMessage.userName, savedBlog)

            return BlogDeletionMessage(true, "Blog ${blogRequestMessage.blogTitle} deleted successfully")
        }catch (e: IllegalArgumentException) {
            e.printStackTrace()
            throw BlogException("A error occurred while deleting blog")
        }
    }

    @Throws(BlogException::class)
    override fun getBlogById(id: ObjectId): Blog {
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

    companion object {
        fun getEmptyBlog(): Blog {
            return Blog(
                ObjectId(),
                "emptyBlog",
                "not Valid",
                Date(System.currentTimeMillis()),
                BlogUser("notValid", "notApplicable", "nothiing@nothing.com", ArrayList(), HashSet()),
                BlogAccessStatus.PRIVATE,
                BlogCategory.EMPTY,
                0,
                ArrayList()
            )
        }
    }
}