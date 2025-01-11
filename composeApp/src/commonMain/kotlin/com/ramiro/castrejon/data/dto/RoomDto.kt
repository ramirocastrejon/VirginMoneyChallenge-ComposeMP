package com.ramiro.castrejon.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomDto(
    @SerialName("createdAt") val createdAt: String,
    @SerialName("id") val id: String,
    @SerialName("isOccupied") val isOccupied: Boolean,
    @SerialName("maxOccupancy") val maxOccupancy: Int
)