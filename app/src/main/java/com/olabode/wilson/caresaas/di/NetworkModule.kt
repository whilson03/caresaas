package com.olabode.wilson.caresaas.di

import android.content.Context
import android.content.SharedPreferences
import com.olabode.wilson.caresaas.api.AuthTokenInterceptor
import com.olabode.wilson.caresaas.api.MainApiService
import com.olabode.wilson.caresaas.di.coroutine.IoDispatcher
import com.olabode.wilson.caresaas.repository.MainRepository
import com.olabode.wilson.caresaas.repository.MainRepositoryImpl
import com.olabode.wilson.caresaas.session.SessionManager
import com.olabode.wilson.caresaas.session.SessionManagerImpl
import com.olabode.wilson.caresaas.utils.AppConstants
import com.olabode.wilson.caresaas.utils.NetworkResultCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authTokenInterceptor: AuthTokenInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authTokenInterceptor)
            .connectTimeout(AppConstants.CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(AppConstants.READ_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshiBuilder(): Moshi {
        return Moshi.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
    }

    @Singleton
    @Provides
    fun provideMainApi(retrofit: Retrofit.Builder): MainApiService {
        return retrofit
            .build()
            .create(MainApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideMainRepository(
        mainApiService: MainApiService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        sessionManager: SessionManager,
        sharedPreferences: SharedPreferences
    ): MainRepository {
        return MainRepositoryImpl(
            mainApiService,
            coroutineDispatcher,
            sessionManager,
            sharedPreferences
        )
    }

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(AppConstants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSessionManager(sharedPreferences: SharedPreferences): SessionManager {
        return SessionManagerImpl(sharedPreferences)
    }
}
