package com.fadymarty.network.data.remote.dto

import com.fadymarty.network.domain.model.Product
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: String,
    val collectionId: String,
    val collectionName: String,
    val created: String,
    val updated: String,
    val title: String,
    val description: String,
    val price: Int,
    val typeCloses: String,
    val type: String,
    @SerialName("approximate_cost")
    val approximateCost: String,
) {
    fun toProduct(): Product {
        return Product(
            id = id,
            collectionId = collectionId,
            collectionName = collectionName,
            created = created,
            updated = updated,
            title = title,
            description = description,
            price = price,
            typeCloses = typeCloses,
            type = type,
            approximateCost = approximateCost
        )
    }
}