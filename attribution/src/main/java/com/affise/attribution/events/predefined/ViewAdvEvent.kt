package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event ViewAdv
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class ViewAdvEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = System.currentTimeMillis(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
    /**
     * Event ViewAdv
     *
     * @property ad the JSON Object describing the meaning of the event.
     * @property timeStampMillis the timestamp event in milliseconds.
     * @property userData any custom string data.
     */
    @Deprecated(
        message = "This constructor will be removed if future",
        replaceWith = ReplaceWith("ViewAdvEvent(userData, timeStampMillis)"),
        level = DeprecationLevel.WARNING
    )
    constructor(
        ad: JSONObject = JSONObject(),
        timeStampMillis: Long = System.currentTimeMillis(),
        userData: String? = null,
    ) : this(
        userData = userData,
        timeStampMillis = timeStampMillis,
    ) {
        anyData = ad
    }
}