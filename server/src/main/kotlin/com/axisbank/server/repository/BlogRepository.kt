package com.axisbank.server.repository

import com.axisbank.server.dto.blog.Blog
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface BlogRepository : MongoRepository<Blog, String> {}