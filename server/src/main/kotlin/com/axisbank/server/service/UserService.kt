@file:Suppress("UNCHECKED_CAST")

package com.axisbank.server.service

import com.axisbank.server.DTO.user.BlogUser
import com.axisbank.server.repository.UserRepository
import com.example.server.DTO.user.Messages
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(final val userRepository: UserRepository) {
    fun authUser(user: BlogUser): Messages.AuthMessage {
        val authMessage = Messages.AuthMessage(user, user, false, "")

        val savedUsers: BlogUser? =
            if (userRepository.findById(user.email).isPresent) userRepository.findById(
                user.email
            ).get() else null
        if (savedUsers == null) {
            authMessage.authNotice = "User Not Found"
        } else {
            if (user.blogPassword == savedUsers.password) {
                authMessage.user = savedUsers
                authMessage.userSignInDetails = user
                authMessage.isAuthenticated = true
                authMessage.authNotice = "Login Successful"
            } else {
                authMessage.authNotice = "Password Failed"
            }
        }
        return authMessage
    }

    fun registerUser(usersRegistrationDetails: BlogUser): Messages.RegistrationMessage {
        var userFound = false
        val users: List<BlogUser> = allUser
        val registrationMessage = Messages.RegistrationMessage("", false, allUser[0])
        if (!users.contains(usersRegistrationDetails)) {
            for (user in users) {
                if (user.email == usersRegistrationDetails.email) {
                    registrationMessage.registrationNotice = "Email Id is used"
                    registrationMessage.user = user
                    registrationMessage.isRegistered = false
                    userFound = true
                }
            }
        } else {
            registrationMessage.user = usersRegistrationDetails
            registrationMessage.registrationNotice = "User Already Exists"
            registrationMessage.isRegistered = false
        }
        if (!userFound) {
            userRepository.save(usersRegistrationDetails)
            registrationMessage.user = usersRegistrationDetails
            registrationMessage.registrationNotice = "User Registered Successfully, You may login now."
            registrationMessage.isRegistered = true
            //Todo: Send email verification
        }
        return registrationMessage
    }

    val allUser: List<BlogUser> = userRepository.findAll()

    fun getUserByEmail(emailId: String): Optional<BlogUser> {
        return userRepository.findById(emailId)
    }
}