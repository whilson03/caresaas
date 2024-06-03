package com.olabode.wilson.caresaas.repository

import android.content.SharedPreferences
import com.olabode.wilson.caresaas.api.MainApiService
import com.olabode.wilson.caresaas.api.request.SignInRequest
import com.olabode.wilson.caresaas.api.response.TaskResponse
import com.olabode.wilson.caresaas.di.coroutine.IoDispatcher
import com.olabode.wilson.caresaas.model.Task
import com.olabode.wilson.caresaas.session.SessionManager
import com.olabode.wilson.caresaas.utils.AppConstants
import com.olabode.wilson.caresaas.utils.BaseApiResponse
import com.olabode.wilson.caresaas.utils.Result
import com.olabode.wilson.caresaas.utils.onError
import com.olabode.wilson.caresaas.utils.onException
import com.olabode.wilson.caresaas.utils.onSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apiService: MainApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val sessionManager: SessionManager,
    private val sharedPreferences: SharedPreferences
) : MainRepository {

    override fun signIn(userName: String, password: String): Flow<Result<String>> = flow {
        apiService.signIn(SignInRequest(userName, password))
            .onSuccess { response ->
                sessionManager.storeAccessToken(response.data.userToken.accessToken)
                sessionManager.storeRefreshToken(response.data.userToken.refreshToken)
                saveSignedInUserDetail(response.data.user.preferredUsername)
                emit(Result.Success(response.data.user.userId))
            }.onError { _, message ->
                emit(Result.Error(message = message))
            }.onException { _, message ->
                emit(Result.Error(message = message))
            }
    }.flowOn(ioDispatcher)

    override fun fetchTasks(
        shortCode: String,
        careHomeId: Int,
        assignee: Int
    ): Flow<Result<List<Task>>> = flow {
        apiService.fetchTasks(shortCode, careHomeId, assignee)
            .onSuccess { response ->
                val taskList = getTasksWithLocation(response)
                emit(Result.Success(taskList))
            }.onError { _, message ->
                emit(Result.Error(message = message))
            }.onException { e, message ->
                emit(Result.Error(message = message))
            }
    }.catch {
        emit(Result.Error(message = AppConstants.UNKNOWN_ERROR))
    }.flowOn(ioDispatcher)

    override fun fetchSignedInUser(): Flow<String> = flow {
        val userName = sharedPreferences.getString(KEY_USER_NAME, "")
        emit(userName.orEmpty())
    }

    override suspend fun saveSignedInUserDetail(name: String) {
        sharedPreferences.edit().putString(KEY_USER_NAME, name).apply()
    }

    private suspend fun getTasksWithLocation(
        response: BaseApiResponse<List<TaskResponse>>
    ): MutableList<Task> {
        val taskList = mutableListOf<Task>()
        response.data.forEach {
            val location = apiService.fetchUserCareLocation(it.userId).data
            taskList.add(
                Task(
                    taskId = it.taskId,
                    roomNumber = location.roomNumber,
                    bedNumber = location.bedNumber,
                    timeOfDay = it.timeOfDay,
                    taskType = it.taskType,
                    assignee = it.taskAssignmentResponses?.first()?.assigneeResponse?.firstName
                )
            )
        }
        return taskList
    }

    private companion object {
        const val KEY_USER_NAME = "KEY_USER_NAME"
    }
}