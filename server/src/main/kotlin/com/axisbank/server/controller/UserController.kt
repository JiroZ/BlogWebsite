package com.axisbank.server.controller


import com.axisbank.server.DTO.user.BlogUser
import com.axisbank.server.service.UserService
import com.example.server.DTO.user.Messages
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
class UserController(val userService: UserService) {
    @RequestMapping(
        value = ["/users/auth"],
        method = [RequestMethod.POST],
        consumes = ["application/json"]
    )
    fun authenticateUser(@RequestBody usersSignInDetails: BlogUser): ResponseEntity<Messages.AuthMessage> {
        val userAuthMessage: Messages.AuthMessage = userService.authUser(usersSignInDetails)
        return ResponseEntity.ok<Messages.AuthMessage>(userAuthMessage)
    }

    @RequestMapping(
        value = ["/users/registration"],
        method = [RequestMethod.POST],
        consumes = ["application/json"]
    )
    private fun registerUser(@RequestBody users: BlogUser): ResponseEntity<Messages.RegistrationMessage> {
        val registrationMessage: Messages.RegistrationMessage = userService.registerUser(users)
        return ResponseEntity.ok<Messages.RegistrationMessage>(registrationMessage)
    }

    @get:GetMapping("/users")
    val allUsers: ResponseEntity<List<Any>>
        get() = ResponseEntity.ok(userService.allUser)

}