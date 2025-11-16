package com.fadymarty.network.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ValidationErrorDto(
    val code: String,
    val message: String,
)
