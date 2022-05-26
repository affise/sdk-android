package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event Rate use
 *
 * @property rate the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class RateEvent(
    private val rate: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize RateEvent to JSONObject
     *
     * @return JSONObject of RateEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_rate", rate)
        put("affise_event_rate_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "Rate"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}