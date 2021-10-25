package com.bloggie.contentservice.repository

import com.bloggie.contentservice.dto.blog.Blog
import org.springframework.data.mongodb.repository.MongoRepository

interface BlogRepository : MongoRepository<Blog, String> {}