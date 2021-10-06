package com.axisbank.server.controller

import com.axisbank.server.dto.Messages.*
import com.axisbank.server.exceptions.BlogException
import com.axisbank.server.exceptions.CommentException
import com.axisbank.server.exceptions.UserException
import com.axisbank.server.service.DefaultUserDetailService

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping(value = ["/user"])
class UserController(
    private val defaultUserDetailService: DefaultUserDetailService
) {
    @RequestMapping(
        value = ["/auth"],
        method = [RequestMethod.POST],
        consumes = ["application/json"]
    )
    @Throws(UserException::class, BlogException::class, CommentException::class)
    fun authenticateUser(
        @RequestBody userAuthRequest: UserAuthRequest
    ): ResponseEntity<UserAuthResponse> {
        val userUserAuthResponse: UserAuthResponse = defaultUserDetailService.authUser(userAuthRequest)
        return ResponseEntity.ok(userUserAuthResponse)
    }

    @RequestMapping(
        value = ["/registration"],
        method = [RequestMethod.POST],
        consumes = ["application/json"]
    )
    @Throws(UserException::class, BlogException::class, CommentException::class)
    private fun registerUser(
        @RequestBody userRegistrationRequest: UserRegistrationRequest
    ): ResponseEntity<RegistrationMessage> {
        val registrationMessage: RegistrationMessage =
            defaultUserDetailService.registerUser(userRegistrationRequest)
        return ResponseEntity.ok(registrationMessage)
    }

    @get:GetMapping
    val allUsers: ResponseEntity<List<Any>> =
        ResponseEntity.ok(defaultUserDetailService.allUser)
}