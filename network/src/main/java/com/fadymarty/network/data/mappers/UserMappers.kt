package com.fadymarty.network.data.mappers

import com.fadymarty.network.data.remote.dto.UserDto
import com.fadymarty.network.domain.model.User

fun UserDto.toUser(): User {
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
        password = password,
        passwordConfirm = passwordConfirm
    )
}

fun User.toUserDto(): UserDto {
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