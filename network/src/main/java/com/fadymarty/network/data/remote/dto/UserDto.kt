package com.fadymarty.network.data.remote.dto

import com.fadymarty.network.domain.model.User
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
    @SerialName("dateBirthday")
    val dateBirthday: String,
    val gender: String,
    val email: String? = null,
    val password: String? = null,
    val passwordConfirm: String? = null,
) {
    fun toUser(): User {
        return User(
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
            password = passwordConfirm,
            passwordConfirm = passwordConfirm
        )
    }
}
