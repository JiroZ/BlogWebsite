package com.bloggie.contentservice.repository

import com.bloggie.contentservice.dto.blog.BlogIndex
import org.springframework.data.mongodb.repository.MongoRepository

interface BlogIndexRepository : MongoRepository<BlogIndex, String> {
}
