package com.affise.attribution.modules.link

import com.affise.attribution.Affise
import com.affise.attribution.modules.AffiseModules

object AffiseLink {

    private val api: AffiseLinkApi?
        get() = module ?: Affise.Module.api<AffiseLinkApi>(AffiseModules.Link).also {
            module = it
        }

    private var module: AffiseLinkApi? = null

    /**
     * Module Link url Resolve
     */
    @JvmStatic
    fun linkResolve(url: String, callback: AffiseLinkCallback) {
        api?.linkResolve(url, callback) ?: callback.handle("")
    }
}

/**
 * Module Link url Resolve
 */
fun Affise.Module.linkResolve(url: String, callback: AffiseLinkCallback) {
    AffiseLink.linkResolve(url, callback)
}