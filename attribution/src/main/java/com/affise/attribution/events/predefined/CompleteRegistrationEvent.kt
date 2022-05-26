package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event CompleteRegistration
 *
 * @property registration the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class CompleteRegistrationEvent(
    private val registration: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize CompleteRegistration to JSONObject
     *
     * @return JSONObject of CompleteRegistration
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_complete_registration", registration)
        put("affise_event_complete_registration_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "CompleteRegistration"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}