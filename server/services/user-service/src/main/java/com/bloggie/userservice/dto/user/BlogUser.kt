package com.bloggie.userservice.dto.user


import com.bloggie.userservice.dto.blog.Blog
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Document(collection = "users")
data class BlogUser(
    @field:Size(min = 3, max = 10, message = "User Name Should Be Between 3 & 10 Characters.")
    @Id val userName: String,

    @JsonIgnore val password: String,

    @field:Pattern(regexp = "^[^@\\s]+@[^@\\s\\.]+\\.[^@\\.\\s]+\$", message = "Incorrect Email ID")
    val email: String,

    @DBRef(db = "blogs", lazy = true)
    val blogs: MutableList<Blog>,

    val blogAuthorities: Set<GrantedAuthority>,
)