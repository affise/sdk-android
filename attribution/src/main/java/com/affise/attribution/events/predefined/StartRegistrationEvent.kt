package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event StartRegistration
 *
 * @property registration the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class StartRegistrationEvent(
    private val registration: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize StartRegistrationEvent to JSONObject
     *
     * @return JSONObject of StartRegistrationEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_start_registration", registration)
        put("affise_event_start_registration_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "StartRegistration"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}