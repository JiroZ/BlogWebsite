package com.axisbank.server.service

import com.axisbank.server.service.contracts.ContentService
import com.axisbank.server.dto.Messages
import com.axisbank.server.dto.blog.Blog
import com.axisbank.server.entities.BlogAccessStatus
import com.axisbank.server.entities.BlogCategory
import com.axisbank.server.entities.BlogCategory.*
import com.axisbank.server.repository.BlogIndexRepository
import com.axisbank.server.service.contracts.PublicProfileService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class DefaultContentService(
    val blogService: DefaultBlogService,
    val blogIndexRepository: BlogIndexRepository,
    val userService: DefaultUserService,
    val publicProfileService: PublicProfileService,
) : ContentService {
    override fun getSearchedContent(personalizedSearchRequest: Messages.PersonalizedSearchRequest): MutableList<Blog> {
        val blogIndexes = blogService.getAllBlogIndexes()
        var blogList = mutableListOf<Blog>()
        val searchString = personalizedSearchRequest.searchString
        val searchedCategory = personalizedSearchRequest.category

        for (blogIndex in blogIndexes) {
            val blog = blogService.getBlogById(blogIndex.blogId)

            when (searchedCategory) {
                ALL -> {
                    if (blog.blogTitle.contains(searchString) || blog.data.contains(searchString)) run {
                        blogList.add(blog)
                    }
                }
                TECHNICAL -> {
                    blogList = addAccordingToCategoryAndSearchString(blog, searchString, blogList, TECHNICAL)
                }

                LIFESTYLE -> {
                    blogList = addAccordingToCategoryAndSearchString(blog, searchString, blogList, LIFESTYLE)
                }

                GAMING -> {
                    blogList = addAccordingToCategoryAndSearchString(blog, searchString, blogList, GAMING)
                }

                ENTERTAINMENT -> {
                    blogList = addAccordingToCategoryAndSearchString(blog, searchString, blogList, ENTERTAINMENT)
                }

                MOVIES -> {
                    blogList = addAccordingToCategoryAndSearchString(blog, searchString, blogList, MOVIES)
                }
            }
        }
        return blogList
    }

    private fun addAccordingToCategoryAndSearchString(
        blog: Blog,
        searchString: String,
        blogList: MutableList<Blog>,
        category: BlogCategory
    ): MutableList<Blog> {
        if (blog.blogCategory == category &&
            (blog.blogTitle.contains(searchString) ||
                    blog.data.contains(searchString))
        ) {
            blogList.add(blog)
        }
        return blogList
    }

    override fun getAccessableContent(): MutableList<Blog> {
        val blogList = mutableListOf<Blog>()
        val blogIndexes = blogService.getAllBlogIndexes()

        val principal: UserDetails
        var username: String = ""
        if(userService.userAuthentication != null) {
            principal = userService.userAuthentication.principal as UserDetails
            username = principal.username
        }

        for (blogIndex in blogIndexes) {
            val blog = blogService.getBlogById(blogIndex.blogId)
            when {
                !userService.isUserAuthenticated || blog.blogAccessStatus == BlogAccessStatus.PUBLIC -> {
                    blogList.add(blog)
                }
                userService.isUserAuthenticated && blog.blogAccessStatus == BlogAccessStatus.PRIVATE && username == blog.owner.userName -> {
                    blogList.add(blog)
                }
                blog.blogAccessStatus == BlogAccessStatus.PRIVATE && blog.sharedWith.contains(
                    publicProfileService.createPublicProfileByUsername(
                        blog.owner.userName
                    )
                ) -> {
                    blogList.add(blog)
                }
            }
        }
        return blogList
    }

    override fun getPrivateContent(): MutableList<Blog> {
        val blogIndexes = blogService.getAllBlogIndexes()
        val blogList = mutableListOf<Blog>()
        val principal = userService.userAuthentication.principal as UserDetails
        val username = principal.username

        for (blogIndex in blogIndexes) {
            val blog = blogService.getBlogById(blogIndex.blogId)

            if ((blog.blogAccessStatus == BlogAccessStatus.PRIVATE && blog.owner.userName == username) ||
                (blog.sharedWith.contains(publicProfileService.createPublicProfileByUsername(username)))
            ) {
                blogList.add(blog)
            }
        }

        return blogList
    }
}