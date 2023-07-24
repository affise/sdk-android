package com.affise.attribution.events.predefined

import com.affise.attribution.events.EventName
import com.affise.attribution.events.NativeEvent
import com.affise.attribution.utils.timestamp

/**
 * Event Rate use
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class ReEngageEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = timestamp(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
    /**
     * Event Rate use
     *
     * @property reEngage the describing the meaning of the event.
     * @property userData any custom string data.
     * @property timeStampMillis the timestamp event in milliseconds.
     */
    @Deprecated(
        message = "This constructor will be removed in future",
        replaceWith = ReplaceWith("ReEngageEvent(userData, timeStampMillis)"),
        level = DeprecationLevel.WARNING
    )
    constructor(
        reEngage: String = "",
        userData: String? = null,
        timeStampMillis: Long = timestamp(),
    ) : this(
        userData = userData,
        timeStampMillis = timeStampMillis,
    ) {
        anyData = reEngage
    }
    override fun getName(): String = EventName.RE_ENGAGE.eventName
}