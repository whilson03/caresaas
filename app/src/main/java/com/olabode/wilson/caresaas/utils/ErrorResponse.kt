package com.olabode.wilson.caresaas.utils

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "message")
    val errorMessage: String? = null
)
