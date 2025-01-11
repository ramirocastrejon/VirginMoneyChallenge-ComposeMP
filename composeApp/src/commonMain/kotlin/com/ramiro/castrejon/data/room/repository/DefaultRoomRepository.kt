package com.ramiro.castrejon.data.room.repository

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import com.plcoding.bookpedia.core.domain.map
import com.ramiro.castrejon.data.room.network.KtorRemoteRoomDataSource
import com.ramiro.castrejon.data.user.mappers.toRoom
import com.ramiro.castrejon.domain.Room
import com.ramiro.castrejon.domain.RoomRepository

class DefaultRoomRepository (
    val remoteRoomDataSource: KtorRemoteRoomDataSource
):RoomRepository{
    override suspend fun getRooms(): Result<List<Room>, DataError.Remote> {
        return remoteRoomDataSource.getRooms().map { dto -> dto.map { it.toRoom() } }
    }

}