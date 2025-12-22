package com.fadymarty.network.data.remote.dto

import com.fadymarty.network.domain.model.AuthResponse
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDto(
    val record: UserDto,
    val token: String,
) {
    fun toAuthResponse(): AuthResponse {
        return AuthResponse(
            record = record.toUser(),
            token = token
        )
    }
}
