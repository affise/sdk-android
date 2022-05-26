package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event CustomId05
 *
 * @property custom the describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class CustomId05Event(
    private val custom: String,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize CustomId05Event to JSONObject
     *
     * @return JSONObject of CustomId05Event
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_custom_id_05", custom)
        put("affise_event_custom_id_05_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "CustomId05"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}