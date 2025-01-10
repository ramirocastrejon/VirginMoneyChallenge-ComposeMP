package com.ramiro.castrejon.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun iniKoin(config: KoinAppDeclaration?= null){
    startKoin {
        config?.invoke(this)
        modules(sharedModule, platformModule)
    }
}