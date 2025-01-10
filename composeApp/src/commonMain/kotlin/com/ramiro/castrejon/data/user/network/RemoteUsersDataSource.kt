package com.ramiro.castrejon.data.user.network

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import com.ramiro.castrejon.data.dto.ResponseUserDto
import com.ramiro.castrejon.data.dto.UserDto

interface RemoteUsersDataSource {
    suspend fun getUser(): Result<List<UserDto>, DataError.Remote>
}