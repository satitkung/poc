package com.example.poc.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage




class FirebaseMessagingService : FirebaseMessagingService() {
    val TAG = "Tee"

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d(TAG, "token: $token")
    }


}