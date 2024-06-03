package com.olabode.wilson.caresaas.api.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "email")
    val email: String,
    @Json(name = "email_verified")
    val emailVerified: Boolean,
    @Json(name = "family_name")
    val familyName: String,
    @Json(name = "given_name")
    val givenName: String,
    @Json(name = "groups")
    val groups: List<String>,
    @Json(name = "lastRole")
    val lastRole: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "organization")
    val organization: String,
    @Json(name = "preferred_username")
    val preferredUsername: String,
    @Json(name = "sub")
    val sub: String,
    @Json(name = "userId")
    val userId: String
)