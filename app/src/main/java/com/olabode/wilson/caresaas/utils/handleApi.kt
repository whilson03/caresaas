package com.olabode.wilson.caresaas.utils


import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

fun <T : Any> handleApi(
    execute: () -> Response<T>
): ApiResult<T> {

    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            ApiSuccess(body)
        } else {
            ApiError(
                code = response.code(),
                message = convertErrorBody(response.errorBody())
                    ?.errorMessage ?: AppConstants.UNKNOWN_ERROR
            )
        }
    } catch (e: HttpException) {
        ApiError(
            code = e.code(),
            message = convertErrorBody(e)?.errorMessage ?: AppConstants.UNKNOWN_ERROR
        )
    } catch (e: Throwable) {
        ApiException(e)
    }
}

private fun convertErrorBody(response: ResponseBody?): ErrorResponse? {
    return try {
        response?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
}
