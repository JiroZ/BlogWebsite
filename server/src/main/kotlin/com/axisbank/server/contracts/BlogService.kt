package com.axisbank.server.contracts

import com.axisbank.server.dto.Messages.*
import com.axisbank.server.dto.blog.Blog
import org.bson.types.ObjectId

interface BlogService {
    fun createBlog(blogRequestMessage: BlogCreateRequestMessage): BlogCreationMessage
    fun updateBlog(blogRequestMessage: BlogRequestMessage): BlogUpdateMessage
    fun deleteBlog(blogRequestMessage: BlogRequestMessage): BlogDeletionMessage
    fun getBlogById(id: ObjectId): Blog
}