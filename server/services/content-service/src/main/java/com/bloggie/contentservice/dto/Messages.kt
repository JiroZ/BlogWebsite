package com.bloggie.contentservice.dto

import com.bloggie.contentservice.dto.blog.BlogIndex
import com.bloggie.contentservice.dto.blog.Comment
import com.bloggie.contentservice.dto.user.BlogUser
import com.bloggie.contentservice.dto.user.PublicProfile
import com.bloggie.contentservice.entities.Action
import com.bloggie.contentservice.entities.BlogAccessStatus
import com.bloggie.contentservice.entities.BlogCategory
import org.springframework.validation.annotation.Validated

@Validated
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
        val blogId: String,
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
        val blogId: String,
        val userName: String
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
        val id: String,
        val blogTitle: String,
        val data: String,
        val userName: String,
        val accessStatus: BlogAccessStatus,
        val blogCategory: BlogCategory,
        val comments: MutableList<Comment>,
        val sharedWith: MutableList<PublicProfile>
    )

    data class BlogCreateRequestMessage(
        val blogTitle: String,
        val data: String,
        val userName: String,
        val accessStatus: BlogAccessStatus,
        val blogCategory: BlogCategory
    )

    data class BlogCommentCreateMessage(
        val blogId: String,
        val comment: String,
        val userName: String
    )

    data class BlogCommentDeletionMessage(
        val blogId: String,
        val commentId: String
    )

    data class BlogCommentUpdateMessage(
        val blogId: String,
        val commentId : String,
        val comment: String
    )

    data class BlogCommentResponse(
        val comment :Comment,
        val notice: String
    )

    data class PersonalizedSearchRequest(
        val category: BlogCategory,
        val searchString: String
    )

    data class UpdateBlogAccessStatusRequest(
        val blogId: String,
        val blogAccessStatus: BlogAccessStatus
    )

    data class UpdateBlogDataRequest(
        val blogId: String,
        val blogData: String
    )

    data class UpdateBlogTitleRequest(
        val blogId: String,
        val blogTitle: String
    )

    data class UpdateBlogViewsRequest(
        val blogId: String,
        val blogViews: Int
    )

    data class UpdateBlogCommentsRequest(
        val blogId: String,
        val comments: MutableList<Comment>,
        val commentAction: Action
    )

    data class UpdateBlogSharedWithRequest(
        val blogId: String,
        val listOfSharedWith: MutableList<PublicProfile>
    )

    data class BlogIndexesResponse(
        val blogIndexes: MutableList<BlogIndex>
    )

}