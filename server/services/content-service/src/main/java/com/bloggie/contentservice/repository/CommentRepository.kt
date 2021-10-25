package com.bloggie.contentservice.repository

import com.bloggie.contentservice.dto.blog.Comment
import org.springframework.data.mongodb.repository.MongoRepository

interface CommentRepository : MongoRepository<Comment, String> {
}