package com.ramiro.castrejon

import android.app.Application
import com.ramiro.castrejon.di.iniKoin
import org.koin.android.ext.koin.androidContext

class MyApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        iniKoin{
            androidContext(this@MyApplication)
        }
    }
}