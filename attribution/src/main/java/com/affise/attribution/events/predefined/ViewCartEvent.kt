package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event ViewCart
 *
 * @property objects the JSON Object describing the meaning of the event.
 * @property userData any custom string data.
 */
class ViewCartEvent(
    private val objects: JSONObject,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize ViewCartEvent to JSONObject
     *
     * @return JSONObject of ViewCartEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_view_cart", objects)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "ViewCart"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}