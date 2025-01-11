package com.ramiro.castrejon.data.room.network

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import com.ramiro.castrejon.core.data.safeCall
import com.ramiro.castrejon.data.dto.RoomDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorRemoteRoomDataSource(
    private val httpClient: HttpClient
): RemoteRoomDataSource {
    override suspend fun getRooms(): Result<List<RoomDto>, DataError.Remote> {
        return safeCall<List<RoomDto>> {
            httpClient.get(
                urlString = "https://61e947967bc0550017bc61bf.mockapi.io/api/v1/rooms"
            )
        }
    }

}