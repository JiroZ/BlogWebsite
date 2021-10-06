package com.axisbank.server.authority

import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import org.springframework.security.core.GrantedAuthority


@Document(collection = "roles")
class Role : GrantedAuthority {
    @MongoId private var id: String? = null

    @DBRef(db = "operation")
    val allowedOperations: List<Operation> = ArrayList()

    override fun getAuthority(): String {
        return id!!
    }
}