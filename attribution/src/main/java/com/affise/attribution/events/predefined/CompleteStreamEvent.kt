package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event CompleteStream
 *
 * @property stream the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class CompleteStreamEvent(
    private val stream: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize CompleteStreamEvent to JSONObject
     *
     * @return JSONObject of CompleteStreamEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_complete_stream", stream)
        put("affise_event_complete_stream_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "CompleteStream"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}