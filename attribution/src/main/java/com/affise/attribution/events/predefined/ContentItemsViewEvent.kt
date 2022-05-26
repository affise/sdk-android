package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONArray
import org.json.JSONObject

/**
 * Event ContentItemsView
 *
 * @property objects the list of JSON Object describing the meaning of the event.
 * @property userData any custom string data.
 */
class ContentItemsViewEvent(
    private val objects: List<JSONObject>,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize ContentItemsViewEvent to JSONObject
     *
     * @return JSONObject of ContentItemsViewEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_content_items_view", JSONArray().apply {
            objects.forEach {
                put(it)
            }
        })
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "ContentItemsView"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}