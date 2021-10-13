package com.axisbank.server.service

import com.axisbank.server.dto.Messages.*
import com.axisbank.server.entities.BlogAccessStatus
import com.axisbank.server.entities.BlogCategory
import com.axisbank.server.exceptions.UserException
import com.axisbank.server.service.contracts.BlogService
import com.axisbank.server.service.contracts.CommentService
import com.axisbank.server.service.contracts.UserService
import org.bson.types.ObjectId
import org.junit.Before
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User

private const val BLOG_USERNAME = "bhavishya"

private const val BLOG_PASSWORD = "1234"

@SpringBootTest
internal class DefaultBlogServiceTest(
    @Autowired val blogService: BlogService,
    @Autowired val userService: UserService,
    @Autowired val commentService: CommentService
) {

    val registrationRequest: UserRegistrationRequest =
        UserRegistrationRequest("bhavishya20@gmail.com", BLOG_USERNAME, BLOG_PASSWORD)
    val authRequest = UserAuthRequest("bhavishya20@gmail.com", BLOG_USERNAME, BLOG_PASSWORD)

    @BeforeEach
    fun `Setup before testing starts`() {
        val message = userService.registerUser(registrationRequest)

        val user = User(message.user!!.userName, BLOG_PASSWORD, listOf(SimpleGrantedAuthority("ROLE_ADMIN")))
        val auth: Authentication = UsernamePasswordAuthenticationToken(user, null)
        SecurityContextHolder.getContext().authentication = auth
    }

    val blogCreateRequestMessage = BlogCreateRequestMessage(
        "Test Blog",
        "Test Blog Data",
        BLOG_USERNAME,
        BlogAccessStatus.PUBLIC,
        BlogCategory.TECHNICAL
    )

    @Test
    fun `Check if blog can be created`() {
        userService.authUser(authRequest)

        val message = blogService.createBlog(blogCreateRequestMessage)

        assertTrue(message.blogCreated)
    }

    @Test
    fun `Check if blog can be deleted`() {
        val authMessage = userService.authUser(authRequest)

        val creationMessage = blogService.createBlog(blogCreateRequestMessage)
        val deletionMessage = blogService.deleteBlog(BlogCallMessage(creationMessage.blogId, authMessage.user!!.userName))

        assertTrue(deletionMessage.blogDeleted)
    }

    @Test
    fun `Check if blog deletion can be failed`() {
        assertThrows(UserException::class.java) {
            val creationMessage = blogService.createBlog(blogCreateRequestMessage)
            blogService.deleteBlog(BlogCallMessage(creationMessage.blogId, "Wrong user"))
        }
    }

    @Test
    fun `Check if comment can be added to blog`() {
        val message = blogService.createBlog(blogCreateRequestMessage)
        val commentMessage = commentService.addComment(BlogCommentCreateMessage(message.blogId, "Test Comment", BLOG_USERNAME))

        assertEquals(commentMessage.comment.commentData, "Test Comment")
    }

    @Test
    fun `Check if comment can be delete from blog`() {
        val message = blogService.createBlog(blogCreateRequestMessage)
        val commentMessage = commentService.addComment(BlogCommentCreateMessage(message.blogId, "Test Comment", BLOG_USERNAME))
        val deleteCommentMessage = commentService.deleteComment(BlogCommentDeletionMessage(message.blogId, commentMessage.comment.commentId))

        assertEquals(deleteCommentMessage.notice, "Comment Removed Successfully")
    }

    @Test
    fun `Check if comment can be updated`() {
        val message = blogService.createBlog(blogCreateRequestMessage)
        val commentMessage = commentService.addComment(BlogCommentCreateMessage(message.blogId, "Test Comment", BLOG_USERNAME))
        val commentUpdateMessage = commentService.updateComment(BlogCommentUpdateMessage(commentMessage.comment.blogId, commentMessage.comment.commentId, "Comment Update message"))

        assertEquals( commentUpdateMessage.notice, "Comment Updated Successfully")
    }


}