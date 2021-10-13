package com.axisbank.server.controller

import com.axisbank.server.service.contracts.BlogUpdateService
import com.axisbank.server.dto.Messages.*
import com.axisbank.server.exceptions.UserException
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/blog/update"])
@Validated
class BlogUpdateManagementController(val blogUpdateService: BlogUpdateService) {
    @PatchMapping("/access")
    fun updateBlogAccessStatus(
        @RequestBody updateBlogAccessStatusRequest: UpdateBlogAccessStatusRequest,
        bindingResult: BindingResult
    ): ResponseEntity<String> {
        bindErrorResult(bindingResult)
        blogUpdateService.updateBlogAccessStatus(updateBlogAccessStatusRequest)

        return ResponseEntity.ok("Access Status Updated")
    }

    @PatchMapping("/data")
    fun updateBlogData(
        @RequestBody updateBlogDataRequest: UpdateBlogDataRequest,
        bindingResult: BindingResult
    ): ResponseEntity<String> {
        bindErrorResult(bindingResult)
        blogUpdateService.updateBlogData(updateBlogDataRequest)

        return ResponseEntity.ok("Your Blog Data has been updated")
    }

    @PatchMapping("/title")
    fun updateBlogTitle(
        @RequestBody updateBlogTitleRequest: UpdateBlogTitleRequest,
        bindingResult: BindingResult
    ): ResponseEntity<String> {
        bindErrorResult(bindingResult)
        blogUpdateService.updateBlogTitle(updateBlogTitleRequest)

        return ResponseEntity.ok("Your blog title has been updated")
    }

    @PatchMapping("/comments")
    fun updateBlogComments(
        @RequestBody updateBlogCommentsRequest: UpdateBlogCommentsRequest,
        bindingResult: BindingResult
    ) {
        bindErrorResult(bindingResult)
        blogUpdateService.updateBlogComments(updateBlogCommentsRequest)

        ResponseEntity.ok("Blog Comments has been updated")
    }

    @PatchMapping("/views")
    fun updateBlogViews(
        @RequestBody updateBlogViewsRequest: UpdateBlogViewsRequest,
        bindingResult: BindingResult
    ): ResponseEntity<String> {
        bindErrorResult(bindingResult)
        blogUpdateService.updateBlogViews(updateBlogViewsRequest)

        return ResponseEntity.ok("Blog views has been updated");
    }

    @PatchMapping("/shared")
    fun updateBlogSharedWith(
        @RequestBody updateBlogSharedWith: UpdateBlogSharedWithRequest,
        bindingResult: BindingResult
    ): ResponseEntity<String> {
        bindErrorResult(bindingResult)
        blogUpdateService.updateBlogSharedWith(updateBlogSharedWith)

        return ResponseEntity.ok("Blog Shared With has been updated");
    }

    private fun bindErrorResult(bindingResult: BindingResult) {
        val errorMessage = StringBuilder()
        if (bindingResult.hasErrors()) {
            val errors = bindingResult.fieldErrors
            for (error in errors) {
                errorMessage.append(error.defaultMessage + " ")
            }
            throw UserException(errorMessage.toString())
        }
    }
}