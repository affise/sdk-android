package com.affise.app.receivers

import com.affise.attribution.Affise
import com.google.firebase.messaging.FirebaseMessagingService

class PushService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Affise.addPushToken(token)
    }
}