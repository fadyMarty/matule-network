package com.fadymarty.network.data.remote.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val identity: String,
    val password: String
)
