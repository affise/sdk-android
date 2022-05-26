package com.affise.app.ui.activity.main

import androidx.lifecycle.ViewModel
import com.affise.attribution.Affise
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : ViewModel(), MainActivityContract.ViewModel {

    init {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            try {
                it.result?.toString()?.let { newToken ->
                    Affise.addPushToken(newToken)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}