package com.fadymarty.network.data.remote.dto

data class PocketbaseResponseDto<T>(
    val page: Int,
    val perPage: Int,
    val totalPages: Int,
    val totalItems: Int,
    val items: List<T>
)