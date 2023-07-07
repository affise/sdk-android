package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONArray

/**
 * Event TravelBooking
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class TravelBookingEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = System.currentTimeMillis(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
    /**
     * Event TravelBooking
     *
     * @property details the JSON array describing the meaning of the event.
     * @property userData any custom string data.
     * @property timeStampMillis the timestamp event in milliseconds.
     */
    @Deprecated(
        message = "This constructor will be removed if future",
        replaceWith = ReplaceWith("TravelBookingEvent(userData, timeStampMillis)"),
        level = DeprecationLevel.WARNING
    )
    constructor(
        details: JSONArray = JSONArray(),
        userData: String? = null,
        timeStampMillis: Long = System.currentTimeMillis(),
    ) : this(
        userData = userData,
        timeStampMillis = timeStampMillis,
    ) {
        anyData = details
    }
}