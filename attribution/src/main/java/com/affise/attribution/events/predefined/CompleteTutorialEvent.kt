package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event CompleteTutorial
 *
 * @property tutorial the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class CompleteTutorialEvent(
    private val tutorial: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize CompleteTutorialEvent to JSONObject
     *
     * @return JSONObject of CompleteTutorialEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_complete_tutorial", tutorial)
        put("affise_event_complete_tutorial_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "CompleteTutorial"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}