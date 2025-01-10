package com.ramiro.castrejon.domain

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result

interface UserRepository {
    suspend fun getUsers(): Result<List<User>, DataError.Remote>
}