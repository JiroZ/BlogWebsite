package com.axisbank.server.service.contracts

import com.axisbank.server.dto.Messages.*


interface UserService {
    fun registerUser(userRegistrationRequest: UserRegistrationRequest): RegistrationMessage;
    fun authUser(userAuthRequest: UserAuthRequest): UserAuthResponse;
}