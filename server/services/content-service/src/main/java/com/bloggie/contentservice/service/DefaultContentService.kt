package com.bloggie.contentservice.service

import com.bloggie.contentservice.dto.Messages
import com.bloggie.contentservice.dto.blog.Blog
import com.bloggie.contentservice.entities.BlogAccessStatus
import com.bloggie.contentservice.entities.BlogCategory
import com.bloggie.contentservice.entities.BlogCategory.*
import com.bloggie.contentservice.service.contracts.ContentService
import com.bloggie.contentservice.service.contracts.PublicProfileService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
open class DefaultContentService(
    private val publicProfileService: PublicProfileService,
    private val requestService: RequestService
) : ContentService {
    override fun getSearchedContent(personalizedSearchRequest: Messages.PersonalizedSearchRequest): MutableList<Blog> {
        val blogIndexes = requestService.getAllBlogIndexes()
        var blogList = mutableListOf<Blog>()
        val searchString = personalizedSearchRequest.searchString
        val searchedCategory = personalizedSearchRequest.category

        for (blogIndex in blogIndexes) {
            val blog = requestService.getBlogById(blogIndex.blogId)

            when (searchedCategory) {
                ALL -> if (blog.blogTitle.contains(searchString) || blog.data.contains(searchString)) run {
                    addAllAccordingToSearchString(blog, searchString, blogList)
                }
                TECHNICAL -> blogList = addAccordingToCategoryAndSearchString(blog, searchString, blogList, TECHNICAL)

                LIFESTYLE -> blogList = addAccordingToCategoryAndSearchString(blog, searchString, blogList, LIFESTYLE)

                GAMING -> blogList = addAccordingToCategoryAndSearchString(blog, searchString, blogList, GAMING)

                ENTERTAINMENT -> blogList =
                    addAccordingToCategoryAndSearchString(blog, searchString, blogList, ENTERTAINMENT)

                MOVIES -> blogList = addAccordingToCategoryAndSearchString(blog, searchString, blogList, MOVIES)
            }
        }
        return blogList
    }

    private fun addAllAccordingToSearchString(
        blog: Blog,
        searchString: String,
        blogList: MutableList<Blog>,
    ): MutableList<Blog> {
        val principal: UserDetails
        var username = ""
        if (requestService.userAuthentication != null) {
            principal = requestService.userAuthentication.principal as UserDetails
            username = principal.username
        }


        if ((blog.blogTitle.contains(searchString) ||
                    blog.data.contains(searchString))
        ) {

            if ((blog.blogAccessStatus == BlogAccessStatus.PUBLIC)) {
                blogList.add(blog)
            } else if (requestService.isUserAuthenticated && blog.blogAccessStatus == BlogAccessStatus.PRIVATE &&
                ((blog.owner.userName == username) ||
                        (blog.sharedWith.contains(publicProfileService.createPublicProfileByUsername(username)))
                        )
            ) {
                blogList.add(blog)
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
        val principal: UserDetails
        var username = ""
        if (requestService.userAuthentication != null) {
            principal = requestService.userAuthentication.principal as UserDetails
            username = principal.username
        }


        if (blog.blogCategory == category &&
            (blog.blogTitle.contains(searchString) ||
                    blog.data.contains(searchString))
        ) {
            if ((blog.blogAccessStatus == BlogAccessStatus.PUBLIC)) {
                blogList.add(blog)
            } else if (requestService.isUserAuthenticated && blog.blogAccessStatus == BlogAccessStatus.PRIVATE &&
                ((blog.owner.userName == username) ||
                        (blog.sharedWith.contains(publicProfileService.createPublicProfileByUsername(username)))
                        )
            ) {
                blogList.add(blog)
            }

        }
        return blogList
    }

    override fun getAccessibleContent(): MutableList<Blog> {
        val blogList = mutableListOf<Blog>()
        val blogIndexes = requestService.getAllBlogIndexes()

        val principal: UserDetails
        var username = ""
        if (requestService.userAuthentication != null) {
            principal = requestService.userAuthentication.principal as UserDetails
            username = principal.username
        }

        for (blogIndex in blogIndexes) {
            val blog = requestService.getBlogById(blogIndex.blogId)
            when {
                (!requestService.isUserAuthenticated && blog.blogAccessStatus == BlogAccessStatus.PUBLIC) || blog.blogAccessStatus == BlogAccessStatus.PUBLIC -> {
                    blogList.add(blog)
                }
                requestService.isUserAuthenticated && blog.blogAccessStatus == BlogAccessStatus.PRIVATE && username == blog.owner.userName -> {
                    blogList.add(blog)
                }

                requestService.isUserAuthenticated && blog.blogAccessStatus == BlogAccessStatus.PRIVATE && blog.sharedWith.contains(
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
        val blogIndexes = requestService.getAllBlogIndexes()
        val blogList = mutableListOf<Blog>()
        val principal = requestService.userAuthentication.principal as UserDetails
        val username = principal.username

        for (blogIndex in blogIndexes) {
            val blog = requestService.getBlogById(blogIndex.blogId)

            if ((blog.blogAccessStatus == BlogAccessStatus.PRIVATE && blog.owner.userName == username) ||
                (blog.sharedWith.contains(publicProfileService.createPublicProfileByUsername(username)))
            ) {
                blogList.add(blog)
            }
        }

        return blogList
    }
}