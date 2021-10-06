package com.axisbank.server.repository

import com.axisbank.server.dto.user.BlogUser
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<BlogUser, String> {
}