package com.affise.attribution.deeplink

import android.net.Uri

/**
 * Manager that coordinates deeplink related tasks
 */
interface DeeplinkManager {
    /**
     * Needs to be called upon app init to properly initialize manager
     */
    fun init()

    /**
     * Sets [callback] to invoke when app receives deeplink
     */
    fun setDeeplinkCallback(callback: OnDeeplinkCallback?)

    /**
     * Process [uri] as deeplink
     */
    fun handleDeeplink(uri: Uri)
}