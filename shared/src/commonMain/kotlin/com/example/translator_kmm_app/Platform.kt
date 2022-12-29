package com.example.translator_kmm_app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform