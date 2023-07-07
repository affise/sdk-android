package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent

/**
 * Event FindLocation
 *
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom data.
 */
class FindLocationEvent(
    private val timeStampMillis: Long = System.currentTimeMillis(),
    private val userData: String? = null,
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
}