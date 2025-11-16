package com.fadymarty.network.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PocketbaseErrorDto(
    val data: Map<String, ValidationErrorDto>,
    val message: String,
    val status: Int,
)
