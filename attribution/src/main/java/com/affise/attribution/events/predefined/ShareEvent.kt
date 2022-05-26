package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event Share
 *
 * @property share the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class ShareEvent(
    private val share: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize ShareEvent to JSONObject
     *
     * @return JSONObject of ShareEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_share", share)
        put("affise_event_share_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "Share"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}