package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import com.affise.attribution.utils.timestamp

/**
 * Event GDPR
 *
 * @property userData any custom string data.
 */
internal class GDPREvent(
    private val userData: String? = null,
) : NativeEvent(
    userData = userData,
    timeStampMillis = timestamp(),
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