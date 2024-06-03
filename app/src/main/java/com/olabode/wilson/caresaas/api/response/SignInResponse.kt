package com.olabode.wilson.caresaas.api.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInResponse(
    @Json(name = "user")
    val user: UserResponse,
    @Json(name = "userToken")
    val userToken: UserToken
)