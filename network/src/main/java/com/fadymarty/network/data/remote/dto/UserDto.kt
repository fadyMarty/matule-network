package com.fadymarty.network.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val collectionId: String? = null,
    val collectionName: String? = null,
    val created: String? = null,
    val emailVisibility: Boolean? = null,
    @SerialName("firstname")
    val firstName: String,
    val id: String? = null,
    @SerialName("lastname")
    val lastName: String,
    @SerialName("secondname")
    val secondName: String,
    val updated: String? = null,
    val verified: Boolean? = null,
    val dateBirthday: String,
    val gender: String,
    val email: String,
    val password: String? = null,
    val passwordConfirm: String? = null,
)
