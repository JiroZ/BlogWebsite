package com.bloggie.blogservice.repository

import com.bloggie.blogservice.dto.blog.BlogIndex
import org.springframework.data.mongodb.repository.MongoRepository

interface BlogIndexRepository : MongoRepository<BlogIndex, String> {
}
