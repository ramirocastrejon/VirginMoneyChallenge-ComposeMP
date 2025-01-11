package com.ramiro.castrejon.data.room.network

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import com.ramiro.castrejon.data.dto.RoomDto

interface RemoteRoomDataSource {
    suspend fun getRooms():Result<List<RoomDto>, DataError.Remote>
}