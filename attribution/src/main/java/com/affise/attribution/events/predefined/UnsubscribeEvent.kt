package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event Unsubscribe
 *
 * @property unsubscribe the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class UnsubscribeEvent(
    private val unsubscribe: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize UnsubscribeEvent to JSONObject
     *
     * @return JSONObject of UnsubscribeEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_unsubscribe", unsubscribe)
        put("affise_event_unsubscribe_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "Unsubscribe"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}