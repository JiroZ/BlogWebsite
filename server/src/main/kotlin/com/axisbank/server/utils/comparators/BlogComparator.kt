package com.axisbank.server.utils.comparators

import com.axisbank.server.dto.blog.Blog

class BlogComparator() : Comparator<Blog>{
    override fun compare(o1: Blog, o2: Blog): Int {
        return o1.views.compareTo(o2.views)
    }
}