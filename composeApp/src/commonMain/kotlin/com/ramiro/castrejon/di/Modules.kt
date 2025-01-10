package com.ramiro.castrejon.di

import com.ramiro.castrejon.core.data.HttpClientFactory
import com.ramiro.castrejon.data.user.network.RemoteUsersDataSource
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import com.ramiro.castrejon.data.user.network.KtorRemoteUserDataSource
import com.ramiro.castrejon.domain.UserRepository
import com.ramiro.castrejon.data.user.repository.DefaultUserRepository
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.ramiro.castrejon.data.user.presentation.UserListViewModel

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteUserDataSource).bind<RemoteUsersDataSource>()
    singleOf(::DefaultUserRepository).bind<UserRepository>()

    viewModelOf(::UserListViewModel)
}