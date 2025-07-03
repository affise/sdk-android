package com.affise.attribution.usecase

import com.affise.attribution.settings.PushTokenService

interface PushTokenUseCase {
    fun addPushToken(pushToken: String, service: PushTokenService)
    fun getPushToken(): String?
    fun getPushTokenService(): String?
}