package com.axisbank.server.utils

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.springframework.stereotype.Component

@Component
class MongoDBUtil(val client: MongoClient) {

    private fun collectionExists(collectionName: String): Boolean {
        return client.getDatabase(DATABASE_NAME).listCollectionNames().contains(collectionName)
    }

    fun createMongoCollectionElseGetCollection(collectionName: String): MongoCollection<Document> {
        return if (collectionExists(collectionName)) {
            client.getDatabase(DATABASE_NAME).getCollection(collectionName)
        } else {
            client.getDatabase(DATABASE_NAME).createCollection(collectionName)
            client.getDatabase(DATABASE_NAME).getCollection(collectionName)
        }
    }

    fun createMongoCollection(collectionName: String) {
        client.getDatabase(DATABASE_NAME).createCollection(collectionName)
    }

    companion object {
        const val DATABASE_NAME = "blog-website"
    }
}