package com.axisbank.server.DTO.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

@Document(collection = "users")
data class BlogUser(
    @Id val blogUsername: String,
    val blogPassword: String,
    val email: String,
    val authorities: Set<GrantedAuthority>
) : User(
    blogUsername,
    blogPassword,
    authorities
)