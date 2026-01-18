package com.fadymarty.network.data.mappers

import com.fadymarty.network.data.remote.dto.AuthResponseDto
import com.fadymarty.network.domain.model.AuthResponse

fun AuthResponseDto.toAuthResponse(): AuthResponse {
    return AuthResponse(
        record = record.toUser(),
        token = token
    )
}