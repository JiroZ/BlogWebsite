package com.axisbank.server.service

import com.axisbank.server.contracts.CommentService
import com.axisbank.server.dto.Messages.*
import com.axisbank.server.dto.blog.Comment
import com.axisbank.server.exceptions.BlogException
import com.axisbank.server.exceptions.CommentException
import com.axisbank.server.exceptions.UserException
import com.axisbank.server.repository.CommentRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.util.*

@Service
class DefaultCommentService(
    val userDetailService: DefaultUserDetailService,
    val commentRepository: CommentRepository,
    val publicProfileService: DefaultPublicProfileService,
    val defaultBlogService: DefaultBlogService
) : CommentService {

    @Throws(CommentException::class, UserException::class, BlogException::class)
    override fun addComment(blogCommentCreateMessage: BlogCommentCreateMessage): BlogCommentResponse {
        try {
            val commentUser = userDetailService.getUserByUsername(blogCommentCreateMessage.userName)

            val blog = defaultBlogService.getBlogById(blogCommentCreateMessage.blogId)

            val comment = Comment(
                blog.id,
                ObjectId(),
                publicProfileService.createPublicProfileByUsername(commentUser.userName),
                blogCommentCreateMessage.comment,
                Date(System.currentTimeMillis())
            )
            commentRepository.save(comment)

            blog.comments.add(comment)

            defaultBlogService.updateBlog(
                BlogRequestMessage(
                    blog.id,
                    blog.blogTitle,
                    blog.data,
                    blog.owner.userName,
                    blog.blogAccessStatus,
                    blog.blogCategory,
                    blog.comments
                )
            )
            return BlogCommentResponse(comment, "Comment Added Successfully")
        }catch (e :IllegalStateException) {
            e.printStackTrace()
            throw CommentException("An error occurred while saving the comment")
        }
    }

    @Throws(CommentException::class, UserException::class, BlogException::class)
    override fun deleteComment(blogCommentDeletionMessage: BlogCommentDeletionMessage): BlogCommentResponse {
        try {
        val blog = defaultBlogService.getBlogById(blogCommentDeletionMessage.blogId)
        val comment = getCommentById(blogCommentDeletionMessage.comment.commentId)

        commentRepository.delete(comment)

        blog.comments.remove(comment)

        defaultBlogService.updateBlog(
            BlogRequestMessage(
                blog.id,
                blog.blogTitle,
                blog.data,
                blog.owner.userName,
                blog.blogAccessStatus,
                blog.blogCategory,
                blog.comments
            )
        )

        return BlogCommentResponse(comment, "Comment Removed Successfully")
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            throw CommentException("An error occurred while deleting the comment")
        }
    }

    @Throws(CommentException::class)
    private fun getCommentById(commentId: ObjectId): Comment {
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
        try {
            val blog = defaultBlogService.getBlogById(blogCommentUpdateMessage.blogId)
            val comment = getCommentById(blogCommentUpdateMessage.commentId)

            val newComment = Comment(
                blog.id,
                comment.commentId,
                comment.commentOwner,
                blogCommentUpdateMessage.comment,
                Date(System.currentTimeMillis())
            )

            commentRepository.save(newComment)

            defaultBlogService.updateBlog(
                BlogRequestMessage(
                    blog.id,
                    blog.blogTitle,
                    blog.data,
                    blog.owner.userName,
                    blog.blogAccessStatus,
                    blog.blogCategory,
                    blog.comments
                )
            )

            return BlogCommentResponse(comment, "Comment Updated")
        }catch (e: IllegalArgumentException) {
            e.printStackTrace()
            throw CommentException("An error occurred while updating the comment")
        }
    }
}