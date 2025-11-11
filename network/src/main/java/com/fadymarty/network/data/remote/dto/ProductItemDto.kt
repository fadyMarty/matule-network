package com.fadymarty.network.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductItemDto(
    val id: String,
    val title: String,
    val price: Int,
    val typeCloses: String,
    val type: String
)
