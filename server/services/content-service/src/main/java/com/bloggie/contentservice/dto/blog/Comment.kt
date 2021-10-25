package com.bloggie.contentservice.dto.blog

import com.bloggie.contentservice.dto.user.PublicProfile
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.validation.constraints.Size

@Document(collection = "comments")
data class Comment(
    val blogId: String,
    @Id val commentId: String,
    val commentOwner: PublicProfile,
    @field:Size(min = 1, max = 300, message = "Comment length Should Be Between 1 & 300 Characters.")
    val commentData: String,
    val commentTimeStamp: Date
)