package com.example.jetpack_chat_application.network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationService {

    @Headers(
        "Authorization: key=" + mFirebaseMessagingService.FCM_SERVER_KEY,
        "Content-Type: application/json"
    )
    @POST("fcm/send")
    fun sendNotification(@Body payload: JsonObject?): Call<JsonObject?>?
}