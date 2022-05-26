package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event Subscribe
 *
 * @property subscribe the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class SubscribeEvent(
    private val subscribe: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize SubscribeEvent to JSONObject
     *
     * @return JSONObject of SubscribeEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_subscribe", subscribe)
        put("affise_event_subscribe_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "Subscribe"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}