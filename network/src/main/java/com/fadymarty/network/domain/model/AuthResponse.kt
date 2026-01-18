package com.fadymarty.network.domain.model

data class AuthResponse(
    val record: User,
    val token: String,
)
