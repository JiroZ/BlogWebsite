package com.bloggie.blogservice.controller

import com.bloggie.blogservice.dto.Messages.*
import com.bloggie.blogservice.dto.blog.Blog
import com.bloggie.blogservice.dto.blog.Comment
import com.bloggie.blogservice.entities.BlogAccessStatus
import com.bloggie.blogservice.exceptions.BlogException
import com.bloggie.blogservice.exceptions.CommentException
import com.bloggie.blogservice.exceptions.UserException
import com.bloggie.blogservice.service.DefaultBlogService
import com.bloggie.blogservice.service.DefaultCommentService
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping(value = ["/blog"])
class BlogController(
    val blogService: DefaultBlogService,
    private val commentService: DefaultCommentService
) {
    @GetMapping
    fun getAllBlogs(): MutableList<Blog> {
        return blogService.getAllBlogs()
    }

    @GetMapping("/{id}")
    @Throws(BlogException::class)
    fun getBlog(
        @PathVariable id: String
    ): ResponseEntity<Blog> {
        val message = blogService.getBlogById(id)
        return ResponseEntity.ok(message)
    }

    @GetMapping("/accesses")
    fun getBlogAccesses(): ResponseEntity<Array<BlogAccessStatus>> {
        return ResponseEntity.ok(BlogAccessStatus.values())
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

    @GetMapping("/comment/{commentId}")
    fun getComment(@PathVariable commentId:String): ResponseEntity<Comment> {
        return ResponseEntity.ok(commentService.getComment(commentId))
    }

    @PostMapping("/create")
    @Throws(UserException::class, BlogException::class, CommentException::class)
    fun createBlog(
        @RequestBody blog: BlogCreateRequestMessage,
        bindingResult: BindingResult
    ): ResponseEntity<BlogCreationMessage> {
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

    @GetMapping("/indexes")
    @Throws(UserException::class, BlogException::class, CommentException::class)
    fun getAllBlogIndexes(): ResponseEntity<BlogIndexesResponse> {
        return ResponseEntity.ok(BlogIndexesResponse(blogService.getAllBlogIndexes()))
    }

    private fun bindErrorResult(bindingResult: BindingResult) {
        val errorMessage = StringBuilder()
        if (bindingResult.hasErrors()) {
            val errors = bindingResult.fieldErrors
            for (error in errors) {
                errorMessage.append(error.defaultMessage!! + " ")
            }
            throw UserException(errorMessage.toString())
        }
    }
}
