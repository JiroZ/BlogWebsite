package com.bloggie.blogservice.service.contracts

import com.bloggie.blogservice.dto.user.PublicProfile

interface PublicProfileService {

    fun createPublicProfileByUsername(username: String): PublicProfile
    fun getBlogCountByUsername(username: String): Int
}
