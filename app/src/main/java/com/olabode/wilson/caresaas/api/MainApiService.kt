package com.olabode.wilson.caresaas.api

import com.olabode.wilson.caresaas.api.request.SignInRequest
import com.olabode.wilson.caresaas.api.response.CareLocationResponse
import com.olabode.wilson.caresaas.api.response.SignInResponse
import com.olabode.wilson.caresaas.api.response.TaskResponse
import com.olabode.wilson.caresaas.utils.ApiResult
import com.olabode.wilson.caresaas.utils.BaseApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApiService {

    @POST("auth/login")
    suspend fun signIn(
        @Body request: SignInRequest
    ): ApiResult<BaseApiResponse<SignInResponse>>

    @GET("tasks/{shortCode}/careHome/{careHomeId}?dateFrom=2024-06-1")
    suspend fun fetchTasks(
        @Path("shortCode") shortCode: String,
        @Path("careHomeId") careHomeId: Int,
        @Query("assignee") assignee: Int
    ): ApiResult<BaseApiResponse<List<TaskResponse>>>

    @GET("users/{userId}/careLocation")
    suspend fun fetchUserCareLocation(
        @Path("userId") userId: Int,
    ): BaseApiResponse<CareLocationResponse>
}