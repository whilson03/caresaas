package com.olabode.wilson.caresaas.ui.home

import com.olabode.wilson.caresaas.model.Task

data class HomeScreenState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val signedUserName: String = ""
)
