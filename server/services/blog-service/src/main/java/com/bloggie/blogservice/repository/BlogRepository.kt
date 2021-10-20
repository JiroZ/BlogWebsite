package com.bloggie.blogservice.repository

import com.bloggie.blogservice.dto.blog.Blog
import org.springframework.data.mongodb.repository.MongoRepository

interface BlogRepository : MongoRepository<Blog, String> {}