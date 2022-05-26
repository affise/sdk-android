package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event StartTutorial
 *
 * @property tutorial the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class StartTutorialEvent(
    private val tutorial: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize StartTutorialEvent to JSONObject
     *
     * @return JSONObject of StartTutorialEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_start_tutorial", tutorial)
        put("affise_event_start_tutorial_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "StartTutorial"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}