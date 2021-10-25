package com.bloggie.blogservice.service

import com.bloggie.blogservice.authority.Authorities
import com.bloggie.blogservice.dto.Messages.*
import com.bloggie.blogservice.dto.blog.Comment
import com.bloggie.blogservice.entities.Action
import com.bloggie.blogservice.exceptions.BlogException
import com.bloggie.blogservice.exceptions.CommentException
import com.bloggie.blogservice.exceptions.UserException
import com.bloggie.blogservice.repository.CommentRepository
import com.bloggie.blogservice.service.contracts.CommentService
import org.bson.types.ObjectId
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
open class DefaultCommentService(
    val commentRepository: CommentRepository,
    val publicProfileService: DefaultPublicProfileService,
    val defaultBlogService: DefaultBlogService,
    val blogUpdateService: DefaultBlogUpdateManagementService,
    val requestService: RequestService
) : CommentService {

    @Throws(CommentException::class, UserException::class, BlogException::class)
    override fun addComment(blogCommentCreateMessage: BlogCommentCreateMessage): BlogCommentResponse {
        try {
            val commentUser = requestService.getUserByUsername(blogCommentCreateMessage.userName)

            val savedBlog = defaultBlogService.getBlogById(blogCommentCreateMessage.blogId)

            val comment = Comment(
                savedBlog.id,
                ObjectId().toString(),
                publicProfileService.createPublicProfileByUsername(commentUser.userName),
                blogCommentCreateMessage.comment,
                Date(System.currentTimeMillis())
            )
            commentRepository.save(comment)

            savedBlog.comments.add(comment)

            blogUpdateService.updateBlogComments(UpdateBlogCommentsRequest(savedBlog.id, savedBlog.comments, Action.ADD))

            return BlogCommentResponse(comment, "Comment Added Successfully")
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            throw CommentException("An error occurred while saving the comment")
        }
    }

    @Throws(CommentException::class, UserException::class, BlogException::class)
    override fun deleteComment(blogCommentDeletionMessage: BlogCommentDeletionMessage): BlogCommentResponse {
        val principal = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val authenticatedUser = principal.username
        val authorities = principal.authorities as Set<*>

        try {

            val savedBlog = defaultBlogService.getBlogById(blogCommentDeletionMessage.blogId)
            val comment = getCommentById(blogCommentDeletionMessage.commentId)
            if (authenticatedUser == savedBlog.owner.userName || authorities.contains(SimpleGrantedAuthority(Authorities.ROLE_ADMIN.toString()))) {
                commentRepository.delete(comment)

                savedBlog.comments.remove(comment)


                blogUpdateService.updateBlogComments(UpdateBlogCommentsRequest(savedBlog.id, savedBlog.comments, Action.ADD))


                return BlogCommentResponse(comment, "Comment Removed Successfully")
            } else {
                throw UserException("Invalid Access")
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            throw CommentException("An error occurred while deleting the comment")
        }
    }

    @Throws(CommentException::class)
    private fun getCommentById(commentId: String): Comment {
        try {
            val comment = commentRepository.findById(commentId)
            return comment.get()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            throw CommentException("Comment not found with id: $commentId")
        }
    }

    @Throws(CommentException::class, BlogException::class)
    override fun updateComment(blogCommentUpdateMessage: BlogCommentUpdateMessage): BlogCommentResponse {
        val principal = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val username = principal.username
        val authorities = principal.authorities as Set<*>

        try {
            val savedBlog = defaultBlogService.getBlogById(blogCommentUpdateMessage.blogId)
            val savedComment = getCommentById(blogCommentUpdateMessage.commentId)
            if (username == savedBlog.owner.userName || authorities.contains(SimpleGrantedAuthority(Authorities.ROLE_ADMIN.toString()))) {

                val newComment = Comment(
                    savedBlog.id,
                    savedComment.commentId,
                    savedComment.commentOwner,
                    blogCommentUpdateMessage.comment,
                    Date(System.currentTimeMillis())
                )
                savedBlog.comments.remove(savedComment)

                commentRepository.save(newComment)

                savedBlog.comments.add(newComment)

                blogUpdateService.updateBlogComments(UpdateBlogCommentsRequest(savedBlog.id, savedBlog.comments, Action.ADD))

                return BlogCommentResponse(savedComment, "Comment Updated Successfully")
            } else {
                throw UserException("Invalid Access")
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            throw CommentException("An error occurred while updating the comment")
        }
    }
}