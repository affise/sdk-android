package com.affise.attribution.events.predefined

import com.affise.attribution.events.EventName
import com.affise.attribution.events.NativeEvent
import com.affise.attribution.utils.timestamp

/**
 * Event CustomizeProduct
 *
 * @property userData any custom data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class CustomizeProductEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = timestamp(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
    override fun getName(): String = EventName.CUSTOMIZE_PRODUCT.eventName
}