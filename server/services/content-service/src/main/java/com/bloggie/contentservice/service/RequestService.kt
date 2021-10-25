package com.bloggie.contentservice.service

import com.bloggie.contentservice.dto.Messages
import com.bloggie.contentservice.dto.blog.Blog
import com.bloggie.contentservice.dto.blog.BlogIndex
import com.bloggie.contentservice.dto.user.BlogUser
import com.bloggie.contentservice.exceptions.BlogException
import com.bloggie.contentservice.exceptions.UserException
import com.bloggie.contentservice.utils.Routes
import com.bloggie.contentservice.utils.Routes.Companion.BLOG_SERVICE
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class RequestService(
    @Autowired
    val webClient: WebClient.Builder
) {
    @Throws(UserException::class)
    fun getUserByUsername(userName: String): BlogUser {
        try {
            return webClient.build()
                .get()
                .uri(Routes.USER_SERVICE + "user/" + userName) //userService . getBlogUserByUsername (blogRequestMessage.userName)
                .retrieve()
                .bodyToMono<BlogUser>()
                .block()!!
        } catch (e: Exception) {
            throw UserException("Problem while catching getting Blog User: " + e.message);
        }
    }

    @Throws(BlogException::class)
    fun getAllBlogIndexes(): MutableList<BlogIndex> {
        try {
            val message =  webClient.build()
                .get()
                .uri(Routes.BLOG_SERVICE + "indexes") //userService . getBlogUserByUsername (blogRequestMessage.userName)
                .retrieve()
                .bodyToMono<Messages.BlogIndexesResponse>()
                .block()!!

            return message.blogIndexes
        } catch (e: Exception) {
            throw BlogException("Problem while fetching Blog Indexes: " + e.message);
        }
    }

    @Throws(BlogException::class)
    fun getBlogById(id: String) : Blog {
        try {
        return webClient.build()
            .get()
            .uri(BLOG_SERVICE + id)
            .retrieve()
            .bodyToMono<Blog>()
            .block()!!
        } catch (e: Exception) {
            throw BlogException("Problem while fetching Blog: " + e.message);
        }
    }

    val userAuthentication = SecurityContextHolder.getContext().authentication
    val isUserAuthenticated = (SecurityContextHolder.getContext().authentication != null)
}