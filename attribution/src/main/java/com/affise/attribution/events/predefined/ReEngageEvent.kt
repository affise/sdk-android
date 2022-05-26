package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event Rate use
 *
 * @property reEngage the describing the meaning of the event.
 * @property userData any custom string data.
 */
class ReEngageEvent(
    private val reEngage: String,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize ReEngageEvent to JSONObject
     *
     * @return JSONObject of ReEngageEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_re_engage", reEngage)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "ReEngage"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}