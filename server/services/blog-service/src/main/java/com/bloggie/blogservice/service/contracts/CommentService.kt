package com.bloggie.blogservice.service.contracts

import com.bloggie.blogservice.dto.Messages.*

interface CommentService {
    fun addComment(blogCommentCreateMessage: BlogCommentCreateMessage): BlogCommentResponse
    fun deleteComment(blogCommentDeletionMessage: BlogCommentDeletionMessage): BlogCommentResponse
    fun updateComment(blogCommentUpdateMessage: BlogCommentUpdateMessage): BlogCommentResponse
}
