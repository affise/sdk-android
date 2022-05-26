package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event ClickAdv
 *
 * @property advertisement the describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class ClickAdvEvent(
    private val advertisement: String,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize ClickAdvEvent to JSONObject
     *
     * @return JSONObject of ClickAdvEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_click_adv", advertisement)
        put("affise_event_click_adv_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "ClickAdv"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}