package com.fadymarty.network.data.remote.dto


import com.fadymarty.network.domain.model.Cart
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartDto(
    val id: String? = null,
    val collectionId: String? = null,
    val collectionName: String? = null,
    val created: String? = null,
    val updated: String? = null,
    @SerialName("user_id")
    val userId: String,
    @SerialName("porduct_id")
    val productId: String,
    val count: Int,
) {
    fun toCart(): Cart {
        return Cart(
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