package com.olabode.wilson.caresaas.repository

import com.olabode.wilson.caresaas.model.CareLocation
import com.olabode.wilson.caresaas.model.Task
import com.olabode.wilson.caresaas.utils.Result
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun signIn(
        userName: String,
        password: String
    ): Flow<Result<String>>

    fun fetchTasks(
        shortCode: String,
        careHomeId: Int,
        assignee: Int
    ): Flow<Result<List<Task>>>

    fun fetchSignedInUser() : Flow<String>

    suspend fun saveSignedInUserDetail(name: String)
}