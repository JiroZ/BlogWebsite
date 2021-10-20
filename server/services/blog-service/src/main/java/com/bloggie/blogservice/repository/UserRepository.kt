package com.bloggie.blogservice.repository

import com.bloggie.blogservice.dto.user.BlogUser
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<BlogUser, String> {
}