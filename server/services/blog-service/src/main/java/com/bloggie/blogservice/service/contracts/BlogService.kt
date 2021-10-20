package com.bloggie.blogservice.service.contracts

import com.bloggie.blogservice.dto.Messages
import com.bloggie.blogservice.dto.blog.Blog
import com.bloggie.blogservice.dto.blog.BlogIndex

interface BlogService {
    fun createBlog(blogRequestMessage: Messages.BlogCreateRequestMessage): Messages.BlogCreationMessage
    fun deleteBlog(blogCallMessage: Messages.BlogCallMessage): Messages.BlogDeletionMessage
    fun getBlogById(id: String): Blog
    fun getBlogIndexById(id: String): BlogIndex
}