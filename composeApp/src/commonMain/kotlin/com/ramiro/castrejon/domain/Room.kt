package com.ramiro.castrejon.domain

data class Room(
    val createdAt: String ="",
    val id: String ="",
    val isOccupied: Boolean =false,
    val maxOccupancy: Int = 0
)