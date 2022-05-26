package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event CustomId03
 *
 * @property custom the describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class CustomId03Event(
    private val custom: String,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize CustomId03Event to JSONObject
     *
     * @return JSONObject of CustomId03Event
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_custom_id_03", custom)
        put("affise_event_custom_id_03_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "CustomId03"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}