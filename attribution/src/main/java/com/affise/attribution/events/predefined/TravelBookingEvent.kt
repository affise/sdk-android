package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONArray
import org.json.JSONObject

/**
 * Event TravelBooking
 *
 * @property details the JSON array describing the meaning of the event.
 * @property userData any custom string data.
 */
class TravelBookingEvent(
    private val details: JSONArray,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize TravelBookingEvent to JSONObject
     *
     * @return JSONObject of TravelBookingEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_travel_booking", details)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "TravelBooking"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}