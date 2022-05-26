package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event Login
 *
 * @property login the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class LoginEvent(
    private val login: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize LoginEvent to JSONObject
     *
     * @return JSONObject of LoginEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_login", login)
        put("affise_event_login_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "Login"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}