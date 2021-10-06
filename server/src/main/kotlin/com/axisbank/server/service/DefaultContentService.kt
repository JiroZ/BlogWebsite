package com.axisbank.server.service

import com.axisbank.server.contracts.ContentService
import com.axisbank.server.dto.blog.Blog
import com.axisbank.server.utils.comparators.BlogComparator
import org.springframework.stereotype.Service
import java.util.*

@Service
class DefaultContentService(
    val blogService: DefaultBlogService,
    val userDetailService: DefaultUserDetailService
) : ContentService {
    override fun getPersonalizedContent() {
        TODO("Not yet implemented")
    }

    override fun getPublicContent(): SortedSet<Blog> {
        return blogService.getAllBlogs().toSortedSet(BlogComparator())
    }

    override fun getPrivateContent() {
        TODO("Not yet implemented")
    }

    override fun requestedOnDemandContent() {
        TODO("Not yet implemented")
    }
}