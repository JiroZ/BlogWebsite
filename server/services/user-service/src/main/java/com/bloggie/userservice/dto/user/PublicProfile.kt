package com.bloggie.userservice.dto.user

data class PublicProfile (
    val userName: String,
    val email: String,
    val blogCount: Int
) {
    companion object {
        fun getEmptyProfile(): PublicProfile {
            return PublicProfile("", "", 0)
        }
    }
}
