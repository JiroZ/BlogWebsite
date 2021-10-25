package com.bloggie.contentservice.repository


import com.bloggie.contentservice.dto.user.BlogUser
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<BlogUser, String> {
}