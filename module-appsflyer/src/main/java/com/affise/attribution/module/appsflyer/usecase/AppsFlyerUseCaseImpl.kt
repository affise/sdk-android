package com.affise.attribution.module.appsflyer.usecase

import com.affise.attribution.events.custom.UserCustomEvent

internal class AppsFlyerUseCaseImpl : AppsFlyerUseCase {
    override fun logEvent(eventName: String, eventValues: Map<String, Any>) {
        UserCustomEvent(eventName, category = CATEGORY)
            .internalAddRawParameters(eventValues)
            .send()
    }

    companion object {
        private const val CATEGORY = "appsflyer"
    }
}