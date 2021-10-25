package com.bloggie.blogservice.service

import com.bloggie.blogservice.authority.Authorities
import com.bloggie.blogservice.dto.Messages.*
import com.bloggie.blogservice.dto.blog.Blog
import com.bloggie.blogservice.exceptions.BlogException
import com.bloggie.blogservice.exceptions.UserException
import com.bloggie.blogservice.repository.BlogRepository
import com.bloggie.blogservice.service.contracts.BlogUpdateService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class DefaultBlogUpdateManagementService(
    val blogService: DefaultBlogService,
    val blogRepository: BlogRepository
) : BlogUpdateService {

    @Throws(BlogException::class, UserException::class)
    override fun updateBlogAccessStatus(updateBlogAccessStatusRequest: UpdateBlogAccessStatusRequest) {
        val principal = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val username = principal.username
        val authorities = principal.authorities as Set<*>

        val savedBlog = blogService.getBlogById(updateBlogAccessStatusRequest.blogId)

        if (username == savedBlog.owner.userName || authorities.contains(SimpleGrantedAuthority(Authorities.ROLE_ADMIN.toString()))) {
            if(savedBlog.blogAccessStatus == updateBlogAccessStatusRequest.blogAccessStatus) {
                throw BlogException("Access Status Request Denied")
            }

            val newBlog = Blog(
                savedBlog.id,
                savedBlog.blogTitle,
                savedBlog.data,
                Date(System.currentTimeMillis()),
                savedBlog.owner,
                updateBlogAccessStatusRequest.blogAccessStatus,
                savedBlog.blogCategory,
                savedBlog.views,
                savedBlog.comments,
                savedBlog.sharedWith
            )
            blogRepository.save(newBlog)
        }
    }

    @Throws(BlogException::class, UserException::class)
    override fun updateBlogData(updateBlogDataRequest: UpdateBlogDataRequest) {
        val principal = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val username = principal.username
        val authorities = principal.authorities as Set<*>

        val savedBlog = blogService.getBlogById(updateBlogDataRequest.blogId)

        if (username == savedBlog.owner.userName || authorities.contains(SimpleGrantedAuthority(Authorities.ROLE_ADMIN.toString()))) {
            if(savedBlog.data == updateBlogDataRequest.blogData) {
                throw BlogException("Blog Data Change Request Denied")
            }

            val newBlog = Blog(
                savedBlog.id,
                savedBlog.blogTitle,
                updateBlogDataRequest.blogData,
                Date(System.currentTimeMillis()),
                savedBlog.owner,
                savedBlog.blogAccessStatus,
                savedBlog.blogCategory,
                savedBlog.views,
                savedBlog.comments,
                savedBlog.sharedWith
            )
            blogRepository.save(newBlog)
        }
    }

    @Throws(BlogException::class, UserException::class)
    override fun updateBlogTitle(updateBlogTitleRequest: UpdateBlogTitleRequest) {
        val principal = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val username = principal.username
        val authorities = principal.authorities as Set<*>

        val savedBlog = blogService.getBlogById(updateBlogTitleRequest.blogId)

        if (username == savedBlog.owner.userName || authorities.contains(SimpleGrantedAuthority(Authorities.ROLE_ADMIN.toString()))) {
            if(savedBlog.blogTitle == updateBlogTitleRequest.blogTitle) {
                throw BlogException("Blog Title Change Request Denied")
            }

            val newBlog = Blog(
                savedBlog.id,
                updateBlogTitleRequest.blogTitle,
                savedBlog.data,
                Date(System.currentTimeMillis()),
                savedBlog.owner,
                savedBlog.blogAccessStatus,
                savedBlog.blogCategory,
                savedBlog.views,
                savedBlog.comments,
                savedBlog.sharedWith
            )
            blogRepository.save(newBlog)
        }
    }

    @Throws(BlogException::class, UserException::class)
    override fun updateBlogComments(updateBlogCommentsRequest: UpdateBlogCommentsRequest) {
        val principal = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val username = principal.username
        val authorities = principal.authorities as Set<*>

        val savedBlog = blogService.getBlogById(updateBlogCommentsRequest.blogId)

        if (username == savedBlog.owner.userName || authorities.contains(SimpleGrantedAuthority(Authorities.ROLE_ADMIN.toString()))) {
            val newBlog = Blog(
                savedBlog.id,
                savedBlog.blogTitle,
                savedBlog.data,
                Date(System.currentTimeMillis()),
                savedBlog.owner,
                savedBlog.blogAccessStatus,
                savedBlog.blogCategory,
                savedBlog.views,
                updateBlogCommentsRequest.comments,
                savedBlog.sharedWith
            )
            blogRepository.save(newBlog)
        }
    }

    @Throws(BlogException::class, UserException::class)
    override fun updateBlogViews(updateBlogViewsRequest: UpdateBlogViewsRequest) {
        val principal = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val username = principal.username
        val authorities = principal.authorities as Set<*>

        val savedBlog = blogService.getBlogById(updateBlogViewsRequest.blogId)

        if (username == savedBlog.owner.userName || authorities.contains(SimpleGrantedAuthority(Authorities.ROLE_ADMIN.toString()))) {
            if (savedBlog.views == updateBlogViewsRequest.blogViews) {
                throw BlogException("Blog Views Change Request Denied")
            }

            val newBlog = Blog(
                savedBlog.id,
                savedBlog.blogTitle,
                savedBlog.data,
                Date(System.currentTimeMillis()),
                savedBlog.owner,
                savedBlog.blogAccessStatus,
                savedBlog.blogCategory,
                updateBlogViewsRequest.blogViews,
                savedBlog.comments,
                savedBlog.sharedWith
            )
            blogRepository.save(newBlog)
        }
    }

    @Throws(BlogException::class, UserException::class)
    override fun updateBlogSharedWith(updateBlogSharedWith: UpdateBlogSharedWithRequest) {
        val principal = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val username = principal.username
        val authorities = principal.authorities as Set<*>

        val savedBlog = blogService.getBlogById(updateBlogSharedWith.blogId)

        if (username == savedBlog.owner.userName || authorities.contains(SimpleGrantedAuthority(Authorities.ROLE_ADMIN.toString()))) {
            if (savedBlog.sharedWith == updateBlogSharedWith.listOfSharedWith) {
                throw BlogException("Blog SharedWith Change Request Denied")
            }

            val newBlog = Blog(
                savedBlog.id,
                savedBlog.blogTitle,
                savedBlog.data,
                Date(System.currentTimeMillis()),
                savedBlog.owner,
                savedBlog.blogAccessStatus,
                savedBlog.blogCategory,
                savedBlog.views,
                savedBlog.comments,
                updateBlogSharedWith.listOfSharedWith
            )
            blogRepository.save(newBlog)
        }
    }
}