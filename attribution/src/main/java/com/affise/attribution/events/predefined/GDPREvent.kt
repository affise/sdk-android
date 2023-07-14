package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent

/**
 * Event GDPR
 *
 * @property userData any custom string data.
 */
internal class GDPREvent(
    private val userData: String? = null,
) : NativeEvent(
    userData = userData,
    timeStampMillis = System.currentTimeMillis(),
    anyData = true
) {

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = EVENT_NAME

    companion object {
        const val EVENT_NAME = "GDPR"
    }
}