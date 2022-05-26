package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event GDPR
 *
 * @property userData any custom string data.
 */
internal class GDPREvent(
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize DeepLinkedEvent to JSONObject
     *
     * @return JSONObject of DeepLinkedEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_gpdr", true)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = EVENT_NAME

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData

    companion object {
        const val EVENT_NAME = "GDPR"
    }
}