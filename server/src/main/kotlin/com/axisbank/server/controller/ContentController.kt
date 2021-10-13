package com.axisbank.server.controller

import com.axisbank.server.dto.Messages.*
import com.axisbank.server.dto.blog.Blog
import com.axisbank.server.service.DefaultContentService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
class ContentController(
    val contentService: DefaultContentService
) {
    @PostMapping("/search")
    fun getSearchedContent(
        @RequestBody personalizedSearchRequest: PersonalizedSearchRequest
    ): ResponseEntity<MutableList<Blog>> {
        return ResponseEntity.ok(contentService.getSearchedContent(personalizedSearchRequest))
    }

    @GetMapping
    fun getPublicContent(): ResponseEntity<MutableList<Blog>> {
        return ResponseEntity.ok(contentService.getAccessableContent())
    }

    @GetMapping("/private")
    fun getPrivateContent(): ResponseEntity<MutableList<Blog>> {
        return ResponseEntity.ok(contentService.getPrivateContent())
    }
}