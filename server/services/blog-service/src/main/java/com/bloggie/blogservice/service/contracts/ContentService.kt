package com.bloggie.blogservice.service.contracts

import com.bloggie.blogservice.dto.Messages.*
import com.bloggie.blogservice.dto.blog.Blog

interface ContentService {
    fun getSearchedContent(personalizedSearchRequest: PersonalizedSearchRequest): MutableList<Blog>
    fun getAccessableContent(): MutableList<Blog>
    fun getPrivateContent(): MutableList<Blog>
}