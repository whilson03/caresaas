package com.olabode.wilson.caresaas.utils

suspend fun <T : Any> ApiResult<T>.onSuccess(
    executable: suspend (T) -> Unit
): ApiResult<T> = apply {
    if (this is ApiSuccess<T>) {
        executable(data)
    }
}

suspend fun <T : Any> ApiResult<T>.onError(
    executable: suspend (code: Int, message: String?) -> Unit
): ApiResult<T> = apply {
    if (this is ApiError<T>) {
        executable(code, message)
    }
}

suspend fun <T : Any> ApiResult<T>.onException(
    executable: suspend (e: Throwable, message: String) -> Unit
): ApiResult<T> = apply {
    if (this is ApiException<T>) {
        executable(e, AppConstants.NETWORK_ERROR)
    }
}
