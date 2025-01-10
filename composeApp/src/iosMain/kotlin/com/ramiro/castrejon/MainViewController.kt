package com.ramiro.castrejon

import androidx.compose.ui.window.ComposeUIViewController
import com.ramiro.castrejon.di.iniKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        iniKoin()
    }
) { App() }