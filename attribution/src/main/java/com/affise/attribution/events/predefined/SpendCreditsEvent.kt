package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event SpendCredits
 *
 * @property credits the value of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class SpendCreditsEvent(
    private val credits: Long,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize SpendCreditsEvent to JSONObject
     *
     * @return JSONObject of SpendCreditsEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_spend_credits", credits)
        put("affise_event_spend_credits_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "SpendCredits"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}