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
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@CrossOrigin
@RestController
@RequestMapping(value = ["/blog"])
@Validated
class BlogController(
    val blogService: DefaultBlogService,
    val commentService: DefaultCommentService
) {
    @GetMapping
    fun getAllBlogs(): ResponseEntity<MutableList<Blog>> {
        return ResponseEntity.ok(blogService.getAllBlogs())
    }

    @GetMapping("/{id}")
    @Throws(UserException::class, BlogException::class, CommentException::class)
    fun getBlog(
        @PathVariable id: String,
        bindingResult: BindingResult
    ): ResponseEntity<Blog> {
        bindErrorResult(bindingResult)

        val message = blogService.getBlogById(id)
        return ResponseEntity.ok(message)
    }

    @DeleteMapping("/delete")
    @Throws(UserException::class, BlogException::class, CommentException::class)
    fun deleteBlog(
        @RequestBody blog: BlogCallMessage,
        bindingResult: BindingResult
    ): ResponseEntity<BlogDeletionMessage> {
        bindErrorResult(bindingResult)

        val message = blogService.deleteBlog(blog)
        return ResponseEntity.ok(message)
    }

    @PostMapping("/create")
    @Throws(UserException::class, BlogException::class, CommentException::class)
    fun createBlog(@RequestBody blog: BlogCreateRequestMessage, bindingResult: BindingResult): ResponseEntity<BlogCreationMessage> {
        bindErrorResult(bindingResult)

        val message = blogService.createBlog(blog)
        return ResponseEntity.ok(message)
    }

    @PostMapping("/comment")
    @Throws(UserException::class, BlogException::class, CommentException::class)
    fun addComment(
        @RequestBody blogCommentCreateMessage: BlogCommentCreateMessage,
        bindingResult: BindingResult
    ): ResponseEntity<BlogCommentResponse> {
        bindErrorResult(bindingResult)

        val message = commentService.addComment(blogCommentCreateMessage)
        return ResponseEntity.ok(message)
    }

    @DeleteMapping("/comment")
    @Throws(UserException::class, BlogException::class, CommentException::class)
    fun deleteComment(
        @RequestBody deletionCommentMessage: BlogCommentDeletionMessage,
        bindingResult: BindingResult
    ): ResponseEntity<BlogCommentResponse> {
        bindErrorResult(bindingResult)

        val message = commentService.deleteComment(deletionCommentMessage)
        return ResponseEntity.ok(message)
    }

    @PatchMapping("/comment")
    @Throws(UserException::class, BlogException::class, CommentException::class)
    fun updateComment(
        @RequestBody updateCommentMessage: BlogCommentUpdateMessage,
        bindingResult: BindingResult
    ): ResponseEntity<BlogCommentResponse> {
        bindErrorResult(bindingResult)

        val message = commentService.updateComment(updateCommentMessage)
        return ResponseEntity.ok(message)
    }

    private fun bindErrorResult(bindingResult: BindingResult) {
        val errorMessage = StringBuilder()
        if (bindingResult.hasErrors()) {
            val errors = bindingResult.fieldErrors
            for (error in errors) {
                errorMessage.append(error.defaultMessage + " ")
            }
            throw UserException(errorMessage.toString())
        }
    }
}
