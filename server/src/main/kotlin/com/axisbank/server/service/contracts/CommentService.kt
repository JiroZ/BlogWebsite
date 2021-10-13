package com.axisbank.server.service.contracts

import com.axisbank.server.dto.Messages

interface CommentService {
    fun addComment(blogCommentCreateMessage: Messages.BlogCommentCreateMessage): Messages.BlogCommentResponse
    fun deleteComment(blogCommentDeletionMessage: Messages.BlogCommentDeletionMessage): Messages.BlogCommentResponse
    fun updateComment(blogCommentUpdateMessage: Messages.BlogCommentUpdateMessage): Messages.BlogCommentResponse
}
