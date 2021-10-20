package com.bloggie.blogservice.service.contracts

import com.bloggie.blogservice.dto.Messages.*


interface UserService {
    fun registerUser(userRegistrationRequest: UserRegistrationRequest): RegistrationMessage;
    fun authUser(userAuthRequest: UserAuthRequest): UserAuthResponse;
}