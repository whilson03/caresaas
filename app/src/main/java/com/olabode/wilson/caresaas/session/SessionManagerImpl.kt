package com.olabode.wilson.caresaas.session

import android.content.SharedPreferences
import javax.inject.Inject

class SessionManagerImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SessionManager {

    override suspend fun storeAccessToken(token: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, token).apply()
    }

    override suspend fun fetchAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, "")
    }

    override suspend fun storeRefreshToken(token: String) {
        sharedPreferences.edit().putString(REFRESH_TOKEN_KEY, token).apply()
    }

    override suspend fun fetchRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, "")
    }

    private companion object {
        const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
        const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
    }
}