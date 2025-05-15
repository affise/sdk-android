package com.affise.attribution.modules.link

import com.affise.attribution.Affise
import com.affise.attribution.modules.AffiseModuleApiWrapper
import com.affise.attribution.modules.AffiseModules

object AffiseLink : AffiseModuleApiWrapper<AffiseLinkApi>(AffiseModules.Link) {
    /**
     * Module Link url Resolve
     */
    @JvmStatic
    fun linkResolve(url: String, callback: AffiseLinkCallback) {
        moduleApi?.linkResolve(url, callback) ?: callback.handle("")
    }
}

/**
 * Module Link url Resolve
 */
fun Affise.Module.linkResolve(url: String, callback: AffiseLinkCallback) {
    AffiseLink.linkResolve(url, callback)
}