package com.axisbank.server.contracts

import com.axisbank.server.dto.user.PublicProfile

interface PublicProfileService {

    fun createPublicProfileByUsername(username: String): PublicProfile
    fun getBlogCountByUsername(username: String): Int
}
