package com.olabode.wilson.caresaas.utils

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>

    data class Error(
        val message: String? = null,
        val exception: Throwable? = null
    ) : Result<Nothing>
}