package com.axisbank.server.service.contracts

import com.axisbank.server.dto.Messages
import com.axisbank.server.dto.blog.Blog

interface ContentService {
    fun getSearchedContent(personalizedSearchRequest: Messages.PersonalizedSearchRequest): MutableList<Blog>
    fun getAccessableContent(): MutableList<Blog>
    fun getPrivateContent(): MutableList<Blog>
}