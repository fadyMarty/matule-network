package com.fadymarty.network.data.remote.dto

import com.fadymarty.network.domain.model.News
import kotlinx.serialization.Serializable

@Serializable
data class NewsDto(
    val collectionId: String,
    val collectionName: String,
    val id: String,
    val newsImage: String,
    val created: String,
    val updated: String,
) {
    fun toNews(): News {
        return News(
            collectionId = collectionId,
            collectionName = collectionName,
            id = id,
            newsImage = newsImage,
            created = created,
            updated = updated
        )
    }
}