package com.affise.attribution.events.predefined

import com.affise.attribution.events.EventName
import com.affise.attribution.events.NativeEvent
import com.affise.attribution.utils.timestamp
import org.json.JSONArray

/**
 * Event Update
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class UpdateEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = timestamp(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
    /**
     * Event Update
     *
     * @property details the JSON array describing the meaning of the event.
     * @property userData any custom string data.
     * @property timeStampMillis the timestamp event in milliseconds.
     */
    @Deprecated(
        message = "This constructor will be removed in future",
        replaceWith = ReplaceWith("UpdateEvent(userData, timeStampMillis)"),
        level = DeprecationLevel.WARNING
    )
    constructor(
        details: JSONArray = JSONArray(),
        userData: String? = null,
        timeStampMillis: Long = timestamp(),
    ) : this(
        userData = userData,
        timeStampMillis = timeStampMillis,
    ) {
        anyData = details
    }
    override fun getName(): String = EventName.UPDATE.eventName
}