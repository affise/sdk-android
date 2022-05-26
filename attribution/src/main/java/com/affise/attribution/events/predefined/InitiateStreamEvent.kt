package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event InitiateStream
 *
 * @property stream the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class InitiateStreamEvent(
    private val stream: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize InitiateStreamEvent to JSONObject
     *
     * @return JSONObject of InitiateStreamEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_initiate_stream", stream)
        put("affise_event_initiate_stream_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "InitiateStream"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}