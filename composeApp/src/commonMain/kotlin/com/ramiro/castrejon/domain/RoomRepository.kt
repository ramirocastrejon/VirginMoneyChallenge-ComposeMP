package com.ramiro.castrejon.domain

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result

interface RoomRepository {
    suspend fun getRooms(): Result<List<Room>, DataError.Remote>
}