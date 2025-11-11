package com.fadymarty.network.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class NewsDto(
    val collectionId: String,
    val collectionName: String,
    val id: String,
    val newsImage: String,
    val created: String,
    val updated: String,
)
