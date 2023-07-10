package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event CompleteRegistration
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class CompleteRegistrationEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = System.currentTimeMillis(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
    /**
     * Event CompleteRegistration
     *
     * @property registration the JSON Object describing the meaning of the event.
     * @property timeStampMillis the timestamp event in milliseconds.
     * @property userData any custom string data.
     */
    @Deprecated(
        message = "This constructor will be removed in future",
        replaceWith = ReplaceWith("CompleteRegistrationEvent(userData, timeStampMillis)"),
        level = DeprecationLevel.WARNING
    )
    constructor(
        registration: JSONObject = JSONObject(),
        timeStampMillis: Long = System.currentTimeMillis(),
        userData: String? = null,
    ) : this(
        userData = userData,
        timeStampMillis = timeStampMillis,
    ) {
        anyData = registration
    }
}