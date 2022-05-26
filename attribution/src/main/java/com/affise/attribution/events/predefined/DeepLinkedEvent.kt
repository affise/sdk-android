package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event DeepLinked
 *
 * @property isLinked event from link or nor
 * @property userData any custom string data.
 */
class DeepLinkedEvent(
    private val isLinked: Boolean,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize DeepLinkedEvent to JSONObject
     *
     * @return JSONObject of DeepLinkedEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_deep_linked", isLinked)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "DeepLinked"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}