package com.axisbank.server.service

import com.axisbank.server.dto.Messages
import com.axisbank.server.exceptions.UserException
import com.axisbank.server.service.contracts.UserService
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@SpringBootTest
class DefaultUserServiceTest(@Autowired val userService: UserService) {

    val registrationRequest: Messages.UserRegistrationRequest =
        Messages.UserRegistrationRequest("bhavishya20@gmail.com", "bhavishya", "1234")
    val authRequest = Messages.UserAuthRequest("bhavishya20@gmail.com", "bhavishya", "1234")

    @Test
    fun `Check if user can be registered successfully`() {
        val message = userService.registerUser(registrationRequest)
        assertTrue(message.isRegistered)
    }

    @Test
    fun `Check if username is already registered`() {
        val message = userService.registerUser(registrationRequest)

        assertThrows(UserException::class.java) {
            userService.registerUser(registrationRequest)
            userService.registerUser(registrationRequest)
        }

        assertTrue(message.isRegistered)
    }

    @Test
    fun `Check if user can be authenticated`() {
        val message = userService.authUser(authRequest)

        assertTrue(message.isAuthenticated)
    }

    @Test()
    fun `Check if authentication is given wrong data`() {
        assertThrows(UserException::class.java) {
            userService.authUser(authRequest)
        }
    }
}



