package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * When a user has some achieve level event.
 *
 * @property level the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class AchieveLevelEvent(
    private val level: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize AchieveLevelEvent to JSONObject
     *
     * @return JSONObject of AchieveLevelEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_achieve_level", level)
        put("affise_event_achieve_level_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "AchieveLevel"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}