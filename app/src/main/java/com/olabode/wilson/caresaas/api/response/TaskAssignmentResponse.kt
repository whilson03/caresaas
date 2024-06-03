package com.olabode.wilson.caresaas.api.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TaskAssignmentResponse(
    @Json(name = "assignee")
    val assigneeResponse: AssigneeResponse,
    @Json(name = "assignmentStatus")
    val assignmentStatus: String?
)