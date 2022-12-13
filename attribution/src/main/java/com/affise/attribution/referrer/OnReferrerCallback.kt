package com.affise.attribution.referrer


/**
 * Interface describing callback that is going to be triggered when Referrer is received by application
 */
fun interface OnReferrerCallback {
    /**
     * Triggered when new Referrer is received by application
     */
    fun handleReferrer(value: String?)
}