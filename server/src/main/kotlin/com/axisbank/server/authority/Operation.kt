package com.axisbank.server.authority

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import org.springframework.security.core.GrantedAuthority


@Document(collection = "operations")
class Operation : GrantedAuthority {
    @MongoId
    private var id: String? = null
    override fun getAuthority(): String {
        return id!!
    }
}