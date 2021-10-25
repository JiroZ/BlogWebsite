package com.bloggie.contentservice.controller

import com.bloggie.contentservice.dto.Messages
import com.bloggie.contentservice.dto.blog.Blog
import com.bloggie.contentservice.service.contracts.ContentService
import org.springframework.core.SpringVersion
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.sql.DriverManager

@RestController

class ContentController(
    private val contentService: ContentService
) {
    @PostMapping("/search")
    fun getSearchedContent(
        @RequestBody personalizedSearchRequest: Messages.PersonalizedSearchRequest
    ): ResponseEntity<MutableList<Blog>> {
        return ResponseEntity.ok(contentService.getSearchedContent(personalizedSearchRequest))
    }

    @GetMapping
    fun getPublicContent(): ResponseEntity<MutableList<Blog>> {
        DriverManager.println("Spring Version : " + SpringVersion.getVersion())
        return ResponseEntity.ok(contentService.getAccessibleContent())
    }

    @GetMapping("/private")
    fun getPrivateContent(): ResponseEntity<MutableList<Blog>> {
        return ResponseEntity.ok(contentService.getPrivateContent())
    }
}