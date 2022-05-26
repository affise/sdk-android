package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event UnlockAchievement
 *
 * @property achievement the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class UnlockAchievementEvent(
    private val achievement: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize UnlockAchievementEvent to JSONObject
     *
     * @return JSONObject of UnlockAchievementEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_unlock_achievement", achievement)
        put("affise_event_unlock_achievement_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "UnlockAchievement"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}