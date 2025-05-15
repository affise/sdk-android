package com.affise.attribution.modules.appsflyer

import com.affise.attribution.modules.AffiseModuleApi

interface AffiseAppsFlyerApi : AffiseModuleApi {
    fun logEvent(eventName: String, eventValues: Map<String, Any>)
}