package com.fadymarty.network.domain.model

data class Order(
    val id: String? = null,
    val collectionId: String? = null,
    val collectionName: String? = null,
    val created: String? = null,
    val updated: String? = null,
    val userId: String,
    val productId: String,
    val count: Int,
)