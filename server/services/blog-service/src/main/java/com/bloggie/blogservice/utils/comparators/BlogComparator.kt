package com.axisbank.server.utils.comparators

import com.axisbank.server.dto.blog.Blog
import com.axisbank.server.dto.blog.BlogIndex

class BlogComparator() : Comparator<BlogIndex>{
    override fun compare(o1: BlogIndex, o2: BlogIndex): Int {
        return o1.views.compareTo(o2.views)
    }
}