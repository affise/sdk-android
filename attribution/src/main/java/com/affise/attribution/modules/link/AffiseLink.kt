package com.affise.attribution.modules.link

import com.affise.attribution.modules.AffiseModuleApiWrapper
import com.affise.attribution.modules.AffiseModules

internal class AffiseLink
    : AffiseModuleApiWrapper<AffiseLinkApi>(AffiseModules.Link),
    AffiseModuleLinkApi {
    /**
     * Module Link url Resolve
     */
    override fun resolve(url: String, callback: AffiseLinkCallback) {
        moduleApi?.resolve(url, callback) ?: callback.handle("")
    }
}
