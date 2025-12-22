package com.fadymarty.network.domain.model

import com.fadymarty.network.data.remote.dto.OrderDto

data class Order(
    val id: String? = null,
    val collectionId: String? = null,
    val collectionName: String? = null,
    val created: String? = null,
    val updated: String? = null,
    val userId: String,
    val productId: String,
    val count: Int,
) {
    fun toOrderDto(): OrderDto {
        return OrderDto(
            id = id,
            collectionId = collectionId,
            collectionName = collectionName,
            created = created,
            updated = updated,
            userId = userId,
            productId = productId,
            count = count
        )
    }
}
