package com.affise.attribution.module.appsflyer.usecase

internal interface AppsFlyerUseCase {

    fun logEvent(eventName: String, eventValues: Map<String, Any>)
}