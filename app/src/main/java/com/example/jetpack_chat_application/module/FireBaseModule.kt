package com.example.jetpack_chat_application.module

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FireBaseModule {

    @Provides
    @Singleton
    fun getFirebaseInstant(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun getFireBaseStorageInstant():FirebaseStorage{
        return FirebaseStorage.getInstance()
    }

}