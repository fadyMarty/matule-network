package com.fadymarty.network.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PocketbaseResponseDto<T>(
    val page: Int,
    val perPage: Int,
    val totalPages: Int,
    val totalItems: Int,
    val items: List<T>,
)