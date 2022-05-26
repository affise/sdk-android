package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event CompleteTrial
 *
 * @property trial the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class CompleteTrialEvent(
    private val trial: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize CompleteTrialEvent to JSONObject
     *
     * @return JSONObject of CompleteTrialEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_complete_trial", trial)
        put("affise_event_complete_trial_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "CompleteTrial"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}