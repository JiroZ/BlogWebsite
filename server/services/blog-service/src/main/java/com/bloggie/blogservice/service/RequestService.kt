package com.bloggie.blogservice.service

import com.bloggie.blogservice.dto.user.BlogUser
import com.bloggie.blogservice.exceptions.UserException
import com.bloggie.blogservice.utils.Routes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class RequestService(
    @Autowired
    val webClient: WebClient.Builder
) {
    fun getUserByUsername(userName: String): BlogUser {
        try {
            return webClient.build()
                .get()
                .uri(Routes.USER_SERVICE + "user/" + userName) //userService . getBlogUserByUsername (blogRequestMessage.userName)
                .retrieve()
                .bodyToMono<BlogUser>()
                .block()!!
        } catch (e: Exception) {
            throw UserException("Problem while catching getting user: " + e.message);
        }
    }
}