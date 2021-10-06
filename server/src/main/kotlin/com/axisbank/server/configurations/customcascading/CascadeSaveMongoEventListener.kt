package com.baeldung.event

import CascadeCallback
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils

@Component
class CascadeSaveMongoEventListener(val mongoOperations: MongoOperations) : AbstractMongoEventListener<Any?>() {
    override fun onBeforeConvert(event: BeforeConvertEvent<Any?>) {
        val source = event.source
        ReflectionUtils.doWithFields(source.javaClass, CascadeCallback(source, mongoOperations))
    }
}