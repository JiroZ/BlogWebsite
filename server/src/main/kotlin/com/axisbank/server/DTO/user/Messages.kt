package com.example.server.DTO.user

import com.axisbank.server.DTO.user.BlogUser
import org.springframework.security.core.userdetails.User

open class Messages {
    data class AuthMessage(
        var userSignInDetails: BlogUser,
        var user: BlogUser,
        var isAuthenticated: Boolean,
        var authNotice: String
    )

    data class RegistrationMessage(
        var registrationNotice: String,
        var isRegistered: Boolean,
        var user: BlogUser
    )
}