package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONArray
import org.json.JSONObject

/**
 * Event Reserve
 *
 * @property reserve the list of JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class ReserveEvent(
    private val reserve: List<JSONObject>,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize ReserveEvent to JSONObject
     *
     * @return JSONObject of ReserveEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_reserve", JSONArray().apply {
            reserve.forEach {
                put(it)
            }
        })
        put("affise_event_reserve_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "Reserve"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}