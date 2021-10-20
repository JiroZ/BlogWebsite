package com.bloggie.userservice.dto.blog

import com.bloggie.userservice.entities.BlogAccessStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "blog_indexes")
data class BlogIndex(
    @Id val blogId: String,
    val blogTitle: String,
    val blogOwnerUsername: String,
    val blogAccessStatus: BlogAccessStatus,
    val views: Int
)