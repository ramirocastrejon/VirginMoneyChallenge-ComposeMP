package com.ramiro.castrejon.data.user.repository

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import com.plcoding.bookpedia.core.domain.map
import com.ramiro.castrejon.data.user.mappers.toUser
import com.ramiro.castrejon.data.user.network.KtorRemoteUserDataSource
import com.ramiro.castrejon.domain.User
import com.ramiro.castrejon.domain.UserRepository

class DefaultUserRepository (
    private val remoteUserDataSource: KtorRemoteUserDataSource
): UserRepository {
    override suspend fun getUsers(): Result<List<User>, DataError.Remote> {
        return remoteUserDataSource.getUser().map { dto -> dto.map { it.toUser() } }
    }

}