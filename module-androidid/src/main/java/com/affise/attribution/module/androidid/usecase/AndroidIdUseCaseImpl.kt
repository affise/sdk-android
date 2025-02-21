package com.affise.attribution.module.androidid.usecase

import android.annotation.SuppressLint
import android.app.Application
import android.provider.Settings

class AndroidIdUseCaseImpl(
    private val app: Application?
): AndroidIdUseCase {

    @SuppressLint("HardwareIds")
    override fun getAndroidId(): String? {
        app ?: return null
        return Settings.Secure.getString(
            app.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }
}