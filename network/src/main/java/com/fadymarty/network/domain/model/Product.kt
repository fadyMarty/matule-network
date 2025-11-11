package com.fadymarty.network.domain.model

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
    val approximateCost: String,
)
