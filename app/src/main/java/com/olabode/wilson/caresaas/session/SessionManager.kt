package com.olabode.wilson.caresaas.session

interface SessionManager {

    suspend fun storeAccessToken(token: String)

    suspend fun fetchAccessToken(): String?

    suspend fun storeRefreshToken(token: String)

    suspend fun fetchRefreshToken(): String?
}
