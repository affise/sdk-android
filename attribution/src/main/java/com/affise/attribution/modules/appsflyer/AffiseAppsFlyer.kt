package com.affise.attribution.modules.appsflyer

import com.affise.attribution.modules.AffiseModuleApiWrapper
import com.affise.attribution.modules.AffiseModules

internal class AffiseAppsFlyer : AffiseModuleApiWrapper<AffiseAppsFlyerApi>(AffiseModules.AppsFlyer), AffiseModuleAppsFlyerApi {
    /**
     * Module AppsFlyer log event
     */
    override fun logEvent(eventName: String, eventValues: Map<String, Any>) {
        moduleApi?.logEvent(eventName, eventValues)
    }
}