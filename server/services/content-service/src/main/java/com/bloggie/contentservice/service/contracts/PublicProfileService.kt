package com.bloggie.contentservice.service.contracts

import com.bloggie.contentservice.dto.user.PublicProfile
import org.springframework.stereotype.Service

@Service
interface PublicProfileService {

    fun createPublicProfileByUsername(username: String): PublicProfile
    fun getBlogCountByUsername(username: String): Int
}
