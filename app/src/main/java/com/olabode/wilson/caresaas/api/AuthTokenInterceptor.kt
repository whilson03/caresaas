package com.olabode.wilson.caresaas.api

import com.olabode.wilson.caresaas.di.coroutine.IoDispatcher
import com.olabode.wilson.caresaas.session.SessionManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthTokenInterceptor @Inject constructor(
    private val sessionManager: SessionManager,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        val accessToken = runBlocking(dispatcher) { sessionManager.fetchAccessToken() }

        val requestBuilder = request
            .newBuilder()
            .method(request.method, request.body)
            .header("Authorization", "Bearer $accessToken")

        return chain.proceed(requestBuilder.build())
    }
}
