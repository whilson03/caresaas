package com.olabode.wilson.caresaas.api.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TaskResponse(
    @Json(name = "action")
    val action: String,
    @Json(name = "hourOfDay")
    val hourOfDay: String?,
    @Json(name = "isAssigned")
    val isAssigned: Boolean,
    @Json(name = "noSupportPersonnel")
    val noSupportPersonnel: String?,
    @Json(name = "order")
    val order: String?,
    @Json(name = "priority")
    val priority: String?,
    @Json(name = "supportLevel")
    val supportLevel: String?,
    @Json(name = "supportPersonnel")
    val supportPersonnel: String?,
    @Json(name = "taskAssignments")
    val taskAssignmentResponses: List<TaskAssignmentResponse>?,
    @Json(name = "taskDate")
    val taskDate: String,
    @Json(name = "taskDetailRef")
    val taskDetailRef: String,
    @Json(name = "taskEndedOn")
    val taskEndedOn: String?,
    @Json(name = "taskGroup")
    val taskGroup: String,
    @Json(name = "taskId")
    val taskId: String,
    @Json(name = "taskScheduleId")
    val taskScheduleId: String,
    @Json(name = "taskStartedOn")
    val taskStartedOn: String?,
    @Json(name = "taskType")
    val taskType: String,
    @Json(name = "timeOfDay")
    val timeOfDay: String?,
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "workStatus")
    val workStatus: String?
)