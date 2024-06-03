package com.olabode.wilson.caresaas.api.response

import com.olabode.wilson.caresaas.model.CareLocation
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CareLocationResponse(
    @Json(name = "roomNumber")
    val roomNumber: String,
    @Json(name = "bedNumber")
    val bedNumber: String
)


fun CareLocationResponse.toCareLocation(): CareLocation {
    return CareLocation(roomNumber, bedNumber)
}