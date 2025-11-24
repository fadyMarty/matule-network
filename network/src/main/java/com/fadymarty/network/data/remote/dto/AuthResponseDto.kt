package com.fadymarty.network.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDto(
    val record: UserDto,
    val token: String,
)
