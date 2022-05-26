package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONArray
import org.json.JSONObject

/**
 * Event ViewItems
 *
 * @property items the JSON array describing the meaning of the event.
 * @property userData any custom string data.
 */
class ViewItemsEvent(
    private val items: JSONArray,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize ViewItemsEvent to JSONObject
     *
     * @return JSONObject of ViewItemsEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_view_items", items)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "ViewItems"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}