package com.fadymarty.network.data.remote.dto

import com.fadymarty.network.domain.model.Order
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    val id: String? = null,
    val collectionId: String? = null,
    val collectionName: String? = null,
    val created: String? = null,
    val updated: String? = null,
    @SerialName("user_id")
    val userId: String,
    @SerialName("product_id")
    val productId: String,
    val count: Int,
) {
    fun toOrder(): Order {
        return Order(
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
