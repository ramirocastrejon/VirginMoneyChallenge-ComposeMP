package com.ramiro.castrejon.data.user.mappers

import com.ramiro.castrejon.data.dto.UserDto
import com.ramiro.castrejon.domain.User

fun UserDto.toUser(): User{
    return User(
        avatar = avatar,
        email = email,
        firstName = firstName,
        lastName = lastName,
        id = id,
        jobtitle = jobtitle,
        createdAt = createdAt

    )
}