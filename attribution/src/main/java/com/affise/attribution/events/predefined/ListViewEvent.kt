package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event ListView
 *
 * @property list the JSON Object describing the meaning of the event.
 * @property userData any custom string data.
 */
class ListViewEvent(
    private val list: JSONObject,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize ListViewEvent to JSONObject
     *
     * @return JSONObject of ListViewEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_list_view", list)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "ListView"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}