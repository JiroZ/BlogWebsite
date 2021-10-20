package com.bloggie.userservice.controller.advisors

import com.bloggie.userservice.exceptions.BlogException
import com.bloggie.userservice.exceptions.CommentException
import com.bloggie.userservice.exceptions.UserException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvisor {

    @ExceptionHandler(UserException::class)
    fun handleUserException(e: UserException) : ResponseEntity<String> {
        return ResponseEntity(e.message,HttpStatus.CONFLICT)
    }

    @ExceptionHandler(BlogException::class)
    fun handleBlogException(e: BlogException) : ResponseEntity<String> {
        return ResponseEntity(e.message,HttpStatus.CONFLICT)
    }

    @ExceptionHandler(CommentException::class)
    fun handleCommentException(e: CommentException) : ResponseEntity<String> {
        return ResponseEntity(e.message,HttpStatus.CONFLICT)
    }

    @ExceptionHandler(Exception::class)
    fun handleDefaultException(e:Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }
}