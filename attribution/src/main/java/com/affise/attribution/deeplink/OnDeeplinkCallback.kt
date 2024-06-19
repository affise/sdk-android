package com.affise.attribution.deeplink


/**
 * Interface describing callback that is going to be triggered when deeplink is received by application
 */
fun interface OnDeeplinkCallback {
    /**
     * Triggered when new deeplink is received by application with
     */
    fun handleDeeplink(value: DeeplinkValue)
}