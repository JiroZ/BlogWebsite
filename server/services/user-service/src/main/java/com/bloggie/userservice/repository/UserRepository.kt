package com.bloggie.userservice.repository

import com.bloggie.userservice.dto.user.BlogUser
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<BlogUser, String> {
}