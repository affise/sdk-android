package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event ViewItem
 *
 * @property item the JSON Object describing the meaning of the event.
 * @property userData any custom string data.
 */
class ViewItemEvent(
    private val item: JSONObject,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize ViewItemEvent to JSONObject
     *
     * @return JSONObject of ViewItemEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_view_item", item)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "ViewItem"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}