package com.affise.attribution.deeplink

import android.net.Uri

/**
 * Interface describing callback that is going to be triggered when deeplink is received by application
 */
fun interface OnDeeplinkCallback {
    /**
     * Triggered when new deeplink [uri] is received by application with
     */
    fun handleDeeplink(uri: Uri): Boolean
}