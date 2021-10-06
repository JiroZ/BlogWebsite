package com.axisbank.server.controller

import com.axisbank.server.dto.Messages.*
import com.axisbank.server.dto.blog.Blog
import com.axisbank.server.exceptions.BlogException
import com.axisbank.server.exceptions.CommentException
import com.axisbank.server.exceptions.UserException
import com.axisbank.server.service.DefaultBlogService
import com.axisbank.server.service.DefaultCommentService
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@CrossOrigin
@RestController
@RequestMapping(value = ["/blog"])
class BlogController(
    val blogService: DefaultBlogService,
    val commentService: DefaultCommentService
) {
    @PostMapping("/get")
    @Throws(UserException::class, BlogException::class, CommentException::class)
    fun getBlog(@RequestBody @Valid blogRequestMessage: BlogRequestMessage,bindingResult: BindingResult): ResponseEntity<Blog> {
        val message = blogService.getBlogById(blogRequestMessage.id)
        return ResponseEntity.ok(message)
    }

    @PostMapping("/update")
    @Throws(UserException::class, BlogException::class, CommentException::class)
    fun updateBlog(blog: BlogRequestMessage): ResponseEntity<BlogUpdateMessage> {
        val message = blogService.updateBlog(blog)
        return ResponseEntity.ok(message)
    }

    @PostMapping("/delete")
    @Throws(UserException::class, BlogException::class, CommentException::class)
    fun deleteBlog(blog: BlogRequestMessage): ResponseEntity<BlogDeletionMessage> {
        val message = blogService.deleteBlog(blog)
        return ResponseEntity.ok(message)
    }

    @PostMapping("/create")
    @Throws(UserException::class, BlogException::class, CommentException::class)
    fun createBlog(blog: BlogCreateRequestMessage): ResponseEntity<BlogCreationMessage> {
        val message = blogService.createBlog(blog)
        return ResponseEntity.ok(message)
    }

    @PostMapping("/comment/add")
    @Throws(UserException::class, BlogException::class, CommentException::class)
    fun addComment(blogCommentCreateMessage: BlogCommentCreateMessage): ResponseEntity<BlogCommentResponse> {
        val message = commentService.addComment(blogCommentCreateMessage)
        return ResponseEntity.ok(message)
    }

    @PostMapping("/comment/delete")
    @Throws(UserException::class, BlogException::class, CommentException::class)
    fun deleteComment(deletionCommentMessage: BlogCommentDeletionMessage): ResponseEntity<BlogCommentResponse> {
        val message = commentService.deleteComment(deletionCommentMessage)
        return ResponseEntity.ok(message)
    }

    @PostMapping("/comment/update")
    @Throws(UserException::class, BlogException::class, CommentException::class)
    fun updateComment(updateCommentMessage: BlogCommentUpdateMessage): ResponseEntity<BlogCommentResponse> {
        val message = commentService.updateComment(updateCommentMessage)
        return ResponseEntity.ok(message)
    }
}
