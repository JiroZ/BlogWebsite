package com.axisbank.server.repository

import com.axisbank.server.dto.blog.Comment
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface CommentRepository : MongoRepository<Comment, String> {
}