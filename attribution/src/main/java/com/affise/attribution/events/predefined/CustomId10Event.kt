package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event CustomId10
 *
 * @property custom the describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class CustomId10Event(
    private val custom: String,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize CustomId10Event to JSONObject
     *
     * @return JSONObject of CustomId10Event
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_custom_id_10", custom)
        put("affise_event_custom_id_10_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "CustomId10"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}