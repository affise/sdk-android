package com.affise.attribution.usecase

import com.affise.attribution.events.Event
import com.affise.attribution.events.OnSendFailedCallback
import com.affise.attribution.events.OnSendSuccessCallback

interface ImmediateSendToServerUseCase {
    fun sendNow(
        event: Event,
        success: OnSendSuccessCallback,
        failed: OnSendFailedCallback,
    )
}