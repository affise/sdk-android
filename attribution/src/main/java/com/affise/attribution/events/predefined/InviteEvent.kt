package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event Invite
 *
 * @property invite the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class InviteEvent(
    private val invite: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize InviteEvent to JSONObject
     *
     * @return JSONObject of InviteEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_invite", invite)
        put("affise_event_invite_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "Invite"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}