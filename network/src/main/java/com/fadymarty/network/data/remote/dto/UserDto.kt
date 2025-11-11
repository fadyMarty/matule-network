package com.fadymarty.network.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val collectionId: String? = null,
    val collectionName: String? = null,
    val created: String? = null,
    val emailVisibility: Boolean,
    val firstname: String,
    val id: String,
    val lastname: String,
    val secondname: String,
    val updated: String? = null,
    val verified: Boolean? = null,
    val datebirthday: String,
    val gender: String,
)
