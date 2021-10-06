package com.axisbank.server.controller

import com.axisbank.server.dto.blog.Blog
import com.axisbank.server.service.DefaultContentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ContentController(
    val contentService: DefaultContentService
) {
    fun getPersonalizedContent() {
        TODO("Not yet implemented")
    }

    @GetMapping
    fun getPublicContent(): ResponseEntity<SortedSet<Blog>> {
        return ResponseEntity.ok(contentService.getPublicContent())
    }

    fun getPrivateContent() {
        TODO("Not yet implemented")
    }

    fun requestedOnDemandContent() {
        TODO("Not yet implemented")
    }
}