package com.bloggie.blogservice.repository

import com.bloggie.blogservice.dto.blog.Comment
import org.springframework.data.mongodb.repository.MongoRepository

interface CommentRepository : MongoRepository<Comment, String> {
}