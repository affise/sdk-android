package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event OpenedFromPushNotification
 *
 * @property details the describing the meaning of the event.
 * @property userData any custom string data.
 */
class OpenedFromPushNotificationEvent(
    private val details: String,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize OpenedFromPushNotification to JSONObject
     *
     * @return JSONObject of OpenedFromPushNotification
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_opened_from_push_notification", details)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "OpenedFromPushNotification"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}