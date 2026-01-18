package com.fadymarty.network.domain.model

data class User(
    val collectionId: String? = null,
    val collectionName: String? = null,
    val created: String? = null,
    val emailVisibility: Boolean? = null,
    val firstName: String,
    val id: String? = null,
    val lastName: String,
    val secondName: String,
    val updated: String? = null,
    val verified: Boolean? = null,
    val dateBirthday: String,
    val gender: String,
    val email: String? = null,
    val password: String? = null,
    val passwordConfirm: String? = null,
)