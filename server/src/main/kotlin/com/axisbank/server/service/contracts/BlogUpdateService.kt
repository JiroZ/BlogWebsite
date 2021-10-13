package com.axisbank.server.service.contracts

import com.axisbank.server.dto.Messages.*

interface BlogUpdateService {
    fun updateBlogAccessStatus(updateBlogAccessStatusRequest: UpdateBlogAccessStatusRequest)
    fun updateBlogData(updateBlogDataRequest: UpdateBlogDataRequest)
    fun updateBlogTitle(updateBlogTitleRequest: UpdateBlogTitleRequest)
    fun updateBlogComments(updateBlogCommentsRequest: UpdateBlogCommentsRequest)
    fun updateBlogViews(updateBlogViewsRequest: UpdateBlogViewsRequest)
    fun updateBlogSharedWith(updateBlogSharedWith: UpdateBlogSharedWithRequest)

}
