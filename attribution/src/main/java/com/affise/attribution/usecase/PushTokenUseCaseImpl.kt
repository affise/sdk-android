package com.affise.attribution.usecase

import android.content.SharedPreferences
import com.affise.attribution.settings.PushTokenService

internal class PushTokenUseCaseImpl(
    private val sharedPreferences: SharedPreferences
) : PushTokenUseCase {

    override fun addPushToken(pushToken: String, service: PushTokenService) {
        sharedPreferences.edit()?.let {
            it.putString(KEY_APP_PUSHTOKEN, pushToken)
            it.putString(KEY_APP_PUSHTOKEN_SERVICE, service.service)
            it.commit()
        }
    }

    override fun getPushToken(): String? {
        return sharedPreferences.getString(KEY_APP_PUSHTOKEN, null)
    }

    override fun getPushTokenService(): String? {
        return sharedPreferences.getString(KEY_APP_PUSHTOKEN_SERVICE, null)
    }

    companion object {
        private const val KEY_APP_PUSHTOKEN = "com.affise.attribution.init.PUSHTOKEN"
        private const val KEY_APP_PUSHTOKEN_SERVICE = "com.affise.attribution.init.PUSHTOKEN_SERVICE"
    }
}