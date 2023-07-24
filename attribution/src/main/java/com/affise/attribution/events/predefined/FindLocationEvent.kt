package com.affise.attribution.events.predefined

import com.affise.attribution.events.EventName
import com.affise.attribution.events.NativeEvent
import com.affise.attribution.utils.timestamp

/**
 * Event FindLocation
 *
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom data.
 */
class FindLocationEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = timestamp(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
    override fun getName(): String = EventName.FIND_LOCATION.eventName
}