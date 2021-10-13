package com.axisbank.server.service

import com.axisbank.server.service.contracts.PublicProfileService
import com.axisbank.server.dto.user.PublicProfile
import com.axisbank.server.exceptions.UserException
import org.springframework.stereotype.Service

@Service
class DefaultPublicProfileService(
    val userService: DefaultUserService
) : PublicProfileService {
    @Throws(UserException::class)
    override fun createPublicProfileByUsername(username: String): PublicProfile {
        val blogUser = userService.getUserByUsername(username)

        return PublicProfile(username, blogUser.email, getBlogCountByUsername(username))
    }

    @Throws(UserException::class)
    override fun getBlogCountByUsername(username: String): Int {
        val blogUser = userService.getUserByUsername(username)

        return blogUser.blogs.size
    }
}