package com.axisbank.server.service.contracts

import com.axisbank.server.dto.Messages.*
import com.axisbank.server.dto.blog.Blog
import com.axisbank.server.dto.blog.BlogIndex

interface BlogService {
    fun createBlog(blogRequestMessage: BlogCreateRequestMessage): BlogCreationMessage
    fun deleteBlog(blogCallMessage: BlogCallMessage): BlogDeletionMessage
    fun getBlogById(id: String): Blog
    fun getBlogIndexById(id: String): BlogIndex
}