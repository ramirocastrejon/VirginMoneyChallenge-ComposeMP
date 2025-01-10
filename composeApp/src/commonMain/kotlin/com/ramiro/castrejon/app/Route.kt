package com.ramiro.castrejon.app

import com.ramiro.castrejon.data.dto.UserDto
import com.ramiro.castrejon.domain.User
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object UserList: Route
    @Serializable
    data object AppGraph: Route
    @Serializable
    data object UserDetail:Route
}