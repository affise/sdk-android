package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event ViewAdv
 *
 * @property ad the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class ViewAdvEvent(
    private val ad: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize ViewAdvEvent to JSONObject
     *
     * @return JSONObject of ViewAdvEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_view_adv", ad)
        put("affise_event_view_adv_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "ViewAdv"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}