package com.axisbank.server.repository

import com.axisbank.server.dto.blog.BlogIndex
import org.springframework.data.mongodb.repository.MongoRepository

interface BlogIndexRepository : MongoRepository<BlogIndex, String> {
}
