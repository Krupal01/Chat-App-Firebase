package com.example.jetpack_chat_application.module

import android.content.Context
import com.example.jetpack_chat_application.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserModule {

    @Provides
    @Singleton
    fun getUserRepository(@ApplicationContext context: Context): UserRepository {
        return UserRepository(context)
    }

}