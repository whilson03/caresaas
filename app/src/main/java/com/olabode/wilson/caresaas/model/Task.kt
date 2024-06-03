package com.olabode.wilson.caresaas.model

data class Task(
    val taskId: String,
    val roomNumber: String?,
    val bedNumber: String?,
    val assignee: String?,
    val timeOfDay: String?,
    val taskType: String?
)
