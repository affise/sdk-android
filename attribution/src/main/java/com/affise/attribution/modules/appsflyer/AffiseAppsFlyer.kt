package com.affise.attribution.modules.appsflyer

import com.affise.attribution.modules.AffiseModuleApiWrapper
import com.affise.attribution.modules.AffiseModules

object AffiseAppsFlyer : AffiseModuleApiWrapper<AffiseAppsFlyerApi>(AffiseModules.AppsFlyer) {
    /**
     * Module AppsFlyer log event
     */
    @JvmStatic
    fun logEvent(eventName: String, eventValues: Map<String, Any>) {
        moduleApi?.logEvent(eventName, eventValues)
    }
}