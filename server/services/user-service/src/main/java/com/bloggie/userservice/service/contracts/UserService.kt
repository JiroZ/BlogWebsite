package com.bloggie.userservice.service.contracts

import com.bloggie.userservice.dto.Messages.*


interface UserService {
    fun registerUser(userRegistrationRequest: UserRegistrationRequest): RegistrationMessage;
    fun authUser(userAuthRequest: UserAuthRequest): UserAuthResponse;
}