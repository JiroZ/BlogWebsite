package com.axisbank.server.contracts

import com.axisbank.server.dto.blog.Blog
import java.util.*

interface ContentService {
    fun getPersonalizedContent()

    fun getPublicContent(): SortedSet<Blog>

    fun getPrivateContent()

    fun requestedOnDemandContent()
}