package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event StartTrial
 *
 * @property trial the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class StartTrialEvent(
    private val trial: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize StartTrialEvent to JSONObject
     *
     * @return JSONObject of StartTrialEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_start_trial", trial)
        put("affise_event_start_trial_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "StartTrial"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}