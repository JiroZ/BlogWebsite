package com.axisbank.server.dto.blog

import com.axisbank.server.dto.user.PublicProfile
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "comments")
data class Comment(
    val blogId: ObjectId,
    val commentId: ObjectId,
    val commentOwner: PublicProfile,
    val commentData: String,
    val commentTimeStamp: Date
) {
    companion object {
        fun getEmptyComment(): Comment {
            return Comment(ObjectId(),ObjectId(), PublicProfile.getEmptyProfile(), "", Date(System.currentTimeMillis()))
        }
    }
}