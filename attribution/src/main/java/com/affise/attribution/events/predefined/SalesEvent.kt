package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event Sales
 *
 * @property salesData the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class SalesEvent(
    private val salesData: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize SalesEvent to JSONObject
     *
     * @return JSONObject of SalesEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_sales", salesData)
        put("affise_event_sales_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "Sales"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}