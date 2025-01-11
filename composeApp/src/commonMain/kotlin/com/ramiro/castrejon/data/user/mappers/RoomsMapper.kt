package com.ramiro.castrejon.data.user.mappers

import com.ramiro.castrejon.data.dto.RoomDto
import com.ramiro.castrejon.domain.Room

fun RoomDto.toRoom(): Room {
    return Room(
        createdAt = createdAt,
        id = id,
        maxOccupancy = maxOccupancy,
        isOccupied = isOccupied
    )
}