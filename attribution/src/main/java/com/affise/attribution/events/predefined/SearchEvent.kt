package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONArray
import org.json.JSONObject

/**
 * Event Search
 *
 * @property search the JSON array describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class SearchEvent(
    private val search: JSONArray,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize SearchEvent to JSONObject
     *
     * @return JSONObject of SearchEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_search", search)
        put("affise_event_search_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "Search"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}