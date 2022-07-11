package com.example.jetpack_chat_application.network

import com.example.jetpack_chat_application.utils.TAG
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class mFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        const val FCM_BASE_URL = "https://fcm.googleapis.com/"
        const val FCM_SERVER_KEY = "AAAAKZKX6y4:APA91bF_CqyOsLv_-y7sjL9Nl5RNd9Gr2n7nlaZqHVMVaZvOdRPJAC5sjZl-C0x98nhKwCCQ5JWEfPHV8paF5adJ2E6JnqIXUfOhnfhxq5dJ4ypgAZuEvScWB50fp2EG8kPvrYymd--j"
        var channelId = "chat_notifications"
        var NOTIFICATION_ID = 1
        var NOT_USER_KEY = "NOT_USER_KEY"
        var NOTIFICATION_REPLY_KEY = "Text"
        var CID = "CID"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i(TAG,token)
    }


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        // handle notification
        Log.i(TAG,"key${message.data.contains("notification").toString()} msg = ${message.data["notification"]}")
    }


}