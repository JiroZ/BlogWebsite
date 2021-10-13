package com.axisbank.server.dto.blog


import com.axisbank.server.configurations.customcascading.CascadeSave
import com.axisbank.server.dto.user.BlogUser
import com.axisbank.server.dto.user.PublicProfile
import com.axisbank.server.entities.BlogAccessStatus
import com.axisbank.server.entities.BlogCategory
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.util.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Size

@Document(collection = "blogs")
data class Blog(
    @Id val id: String,

    @field:Size(min = 3, max = 30, message = "Blog Title Should Be Between 3 & 30 Characters.")
    val blogTitle: String,

    @field:Size(min = 3, max = 50000, message = "Blog range Should Be Between 3 & 50,000 Characters.")
    val data: String,

    val date: Date,
    val owner: PublicProfile,
    val blogAccessStatus: BlogAccessStatus,
    val blogCategory: BlogCategory,
    val views: Int,
    @DBRef(db = "comments") val comments: MutableList<Comment>,
    val sharedWith: MutableList<PublicProfile>
)