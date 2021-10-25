package com.bloggie.contentservice.utils.comparators

import com.bloggie.contentservice.dto.blog.BlogIndex

class BlogComparator() : Comparator<BlogIndex>{
    override fun compare(o1: BlogIndex, o2: BlogIndex): Int {
        return o1.views.compareTo(o2.views)
    }
}