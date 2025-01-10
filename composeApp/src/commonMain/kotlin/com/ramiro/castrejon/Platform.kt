package com.ramiro.castrejon

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform