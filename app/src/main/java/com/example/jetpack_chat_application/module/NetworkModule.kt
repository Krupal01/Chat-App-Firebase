package com.example.jetpack_chat_application.module

import com.example.jetpack_chat_application.network.NotificationService
import com.example.jetpack_chat_application.network.mFirebaseMessagingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun getClient():OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val request = chain.request()
                chain.proceed(request)
            }).build()
    }

    @Provides
    @Singleton
    fun getNotificationService(client: OkHttpClient) : NotificationService{
        return Retrofit.Builder()
            .baseUrl(mFirebaseMessagingService.FCM_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NotificationService::class.java)
    }
}