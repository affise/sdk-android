package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event CustomId02
 *
 * @property custom the describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class CustomId02Event(
    private val custom: String,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize CustomId02Event to JSONObject
     *
     * @return JSONObject of CustomId02Event
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
    override fun getName() = "CustomId02"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}