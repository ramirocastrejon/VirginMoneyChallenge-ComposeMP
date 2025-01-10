package com.ramiro.castrejon.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("createdAt") val createdAt: String,
    @SerialName("avatar") val avatar:String,
    @SerialName("email") val email:String,
    @SerialName("firstName") val firstName:String,
    @SerialName("id") val id: String,
    @SerialName("jobtitle") val jobtitle: String,
    @SerialName("lastName") val lastName:String,

        )