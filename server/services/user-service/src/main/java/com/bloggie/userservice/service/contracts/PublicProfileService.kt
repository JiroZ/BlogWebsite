package com.bloggie.userservice.service.contracts

import com.bloggie.userservice.dto.user.PublicProfile

interface PublicProfileService {

    fun createPublicProfileByUsername(username: String): PublicProfile
    fun getBlogCountByUsername(username: String): Int
}
