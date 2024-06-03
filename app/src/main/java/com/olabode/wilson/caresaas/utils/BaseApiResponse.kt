package com.olabode.wilson.caresaas.utils

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class BaseApiResponse<T>(
    val data: T,
    val status: String,
    val message: String,
    val code: Int
)
