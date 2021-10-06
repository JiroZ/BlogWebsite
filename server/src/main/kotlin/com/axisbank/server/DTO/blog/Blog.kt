package com.axisbank.server.dto.blog


import com.axisbank.server.configurations.customcascading.CascadeSave
import com.axisbank.server.dto.user.BlogUser
import com.axisbank.server.entities.BlogAccessStatus
import com.axisbank.server.entities.BlogCategory
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.util.*

@Document(collection = "blogs")
data class Blog(
    @Id val id: ObjectId,
    val blogTitle: String,
    val data: String,
    val date: Date,
    @DBRef(db = "users") val owner: BlogUser,
    val blogAccessStatus: BlogAccessStatus,
    val blogCategory: BlogCategory,
    val views: Int,
    @DBRef(db = "comments", lazy = true)
    val comments: MutableList<Comment>
)