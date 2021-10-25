package com.bloggie.contentservice.service.contracts

import com.bloggie.contentservice.dto.Messages
import com.bloggie.contentservice.dto.blog.Blog

interface ContentService {
    fun getSearchedContent(personalizedSearchRequest: Messages.PersonalizedSearchRequest): MutableList<Blog>
    fun getAccessibleContent(): MutableList<Blog>
    fun getPrivateContent(): MutableList<Blog>
}