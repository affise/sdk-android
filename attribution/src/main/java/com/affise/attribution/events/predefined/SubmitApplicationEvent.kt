package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent

/**
 * Event SubmitApplication
 *
 * @property userData any custom data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class SubmitApplicationEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = System.currentTimeMillis(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
}