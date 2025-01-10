package com.ramiro.castrejon.data.user.network

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import com.ramiro.castrejon.core.data.safeCall
import com.ramiro.castrejon.data.dto.ResponseUserDto
import com.ramiro.castrejon.data.dto.UserDto
import com.ramiro.castrejon.domain.User
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorRemoteUserDataSource (
    private val httpClient: HttpClient
): RemoteUsersDataSource {
    override suspend fun getUser(): Result<List<UserDto>, DataError.Remote> {
        return safeCall<List<UserDto>> {
            httpClient.get(
            urlString = "https://61e947967bc0550017bc61bf.mockapi.io/api/v1/people"
            )
        }
    }
}