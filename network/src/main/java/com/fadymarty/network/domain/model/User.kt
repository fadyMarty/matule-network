package com.fadymarty.network.domain.model

import com.fadymarty.network.data.remote.dto.UserDto

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
) {
    fun toUserDto(): UserDto {
        return UserDto(
            collectionId = collectionId,
            collectionName = collectionName,
            created = created,
            emailVisibility = emailVisibility,
            firstName = firstName,
            id = id,
            lastName = lastName,
            secondName = secondName,
            updated = updated,
            verified = verified,
            dateBirthday = dateBirthday,
            gender = gender,
            email = email,
            password = password,
            passwordConfirm = passwordConfirm
        )
    }
}
