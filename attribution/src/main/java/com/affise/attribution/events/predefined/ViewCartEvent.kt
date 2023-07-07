package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event ViewCart
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class ViewCartEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = System.currentTimeMillis(),
) : NativeEvent(
    userData = userData, timeStampMillis = timeStampMillis
) {
    /**
     * Event ViewCart
     *
     * @property objects the JSON Object describing the meaning of the event.
     * @property userData any custom string data.
     * @property timeStampMillis the timestamp event in milliseconds.
     */
    @Deprecated(
        message = "This constructor will be removed if future",
        replaceWith = ReplaceWith("ViewCartEvent(userData, timeStampMillis)"),
        level = DeprecationLevel.WARNING
    )
    constructor(
        objects: JSONObject = JSONObject(),
        userData: String? = null,
        timeStampMillis: Long = System.currentTimeMillis(),
    ) : this(
        userData = userData,
        timeStampMillis = timeStampMillis,
    ) {
        anyData = objects
    }
}