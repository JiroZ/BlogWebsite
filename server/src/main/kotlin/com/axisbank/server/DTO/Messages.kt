package com.axisbank.server.dto

import com.axisbank.server.dto.blog.Blog
import com.axisbank.server.dto.blog.Comment
import com.axisbank.server.dto.user.BlogUser
import com.axisbank.server.entities.BlogAccessStatus
import com.axisbank.server.entities.BlogCategory
import org.bson.types.ObjectId

open class Messages {

    data class UserAuthResponse(
        var user: BlogUser?,
        var isAuthenticated: Boolean,
        var authNotice: String,
        var token: String
    )

    data class RegistrationMessage(
        var registrationNotice: String,
        var isRegistered: Boolean,
        var user: BlogUser?
    )

    data class BlogCreationMessage(
        val blogCreated: Boolean,
        val notice: String
    )

    data class BlogDeletionMessage(
        val blogDeleted: Boolean,
        val notice: String
    )

    data class BlogUpdateMessage(
        val blogUpdated: Boolean,
        val notice: String
    )

    data class BlogCallMessage(
        val blogFound: Boolean,
        val notice: String,
        val blog: Blog
    )

    data class UserRegistrationRequest(
        val email: String,
        val userName: String,
        val password: String
    )

    data class UserAuthRequest(
        val email: String,
        val userName: String,
        val password: String
    )

    data class BlogRequestMessage(
        val id: ObjectId,
        val blogTitle: String,
        val data: String,
        val userName: String,
        val accessStatus: BlogAccessStatus,
        val blogCategory: BlogCategory,
        val comments: MutableList<Comment>
    )

    data class BlogCreateRequestMessage(
        val blogTitle: String,
        val data: String,
        val userName: String,
        val accessStatus: BlogAccessStatus,
        val blogCategory: BlogCategory
    )

    data class BlogCommentCreateMessage(
        val blogId: ObjectId,
        val comment: String,
        val userName: String
    )

    data class BlogCommentDeletionMessage(
        val blogId: ObjectId,
        val comment: Comment
    )

    data class BlogCommentUpdateMessage(
        val blogId: ObjectId,
        val commentId : ObjectId,
        val comment: String
    )

    data class BlogCommentResponse(
        val comment :Comment,
        val notice: String
    )

}