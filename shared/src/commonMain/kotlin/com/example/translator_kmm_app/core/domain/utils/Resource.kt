package com.example.translator_kmm_app.core.domain.utils

sealed class Resource<T>(val data: T?, val throwable: Throwable? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(throwable: Throwable) : Resource<T>(null, throwable)
}
