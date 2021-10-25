package com.bloggie.contentservice.service

import com.bloggie.contentservice.dto.user.PublicProfile
import com.bloggie.contentservice.exceptions.UserException
import com.bloggie.contentservice.service.contracts.PublicProfileService
import org.springframework.stereotype.Service

@Service
class DefaultPublicProfileService( val requestService: RequestService
) : PublicProfileService {
    @Throws(UserException::class)
    override fun createPublicProfileByUsername(username: String): PublicProfile {
        val blogUser = requestService.getUserByUsername(username)

        return PublicProfile(username, blogUser.email, getBlogCountByUsername(username))
    }

    @Throws(UserException::class)
    override fun getBlogCountByUsername(username: String): Int {
        val blogUser = requestService.getUserByUsername(username)
        return blogUser.blogs.size
    }
}