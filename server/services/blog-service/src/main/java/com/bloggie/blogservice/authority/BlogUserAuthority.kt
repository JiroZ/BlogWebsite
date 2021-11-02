package com.bloggie.blogservice.authority

import org.springframework.security.core.GrantedAuthority

class BlogUserAuthority(val role: String): GrantedAuthority {

    override fun getAuthority(): String {
        return role
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        return if (other is BlogUserAuthority) {
            role == other.role
        } else false
    }

    override fun hashCode(): Int {
        return role.hashCode()
    }

    override fun toString(): String {
        return role
    }
}