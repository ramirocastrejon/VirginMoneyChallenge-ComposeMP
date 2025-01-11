package com.ramiro.castrejon.data.room.presentation

import com.ramiro.castrejon.domain.Room

data class RoomListState(
    val roomResults:List<Room> = emptyList(),
    val isLoading: Boolean = true
)