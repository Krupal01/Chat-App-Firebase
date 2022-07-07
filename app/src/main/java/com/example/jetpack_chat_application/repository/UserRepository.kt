package com.example.jetpack_chat_application.repository

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.jetpack_chat_application.model.MainUserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

const val USER_DATASTORE_NAME = "logged User"
const val USER_KEY = "USER_KEY"
const val PASSWORD_KEY = "PASSWORD_KEY"

class UserRepository(private val context: Context) {

    suspend fun setUser(email : String, password : String){
        context.UserPreference.edit {
            it[stringPreferencesKey(USER_KEY)] = email
            it[stringPreferencesKey(PASSWORD_KEY)] = password
        }
    }

    val userFlow : Flow<MainUserModel>
        get() = context.UserPreference.data.map {
            MainUserModel(
                it[stringPreferencesKey(USER_KEY)],
                it[stringPreferencesKey(PASSWORD_KEY)]
            )
        }.catch { exception ->
            Log.i("DataStore Exception",exception.toString())
        }

    companion object{
        private val Context.UserPreference by preferencesDataStore(name = USER_DATASTORE_NAME )
    }
}