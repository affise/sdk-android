package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONArray
import org.json.JSONObject

/**
 * Event Update
 *
 * @property details the JSON array describing the meaning of the event.
 * @property userData any custom string data.
 */
class UpdateEvent(
    private val details: JSONArray,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize UpdateEvent to JSONObject
     *
     * @return JSONObject of UpdateEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_update", details)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "Update"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}