package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event Purchase use
 *
 * @property purchaseData the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class PurchaseEvent(
    private val purchaseData: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize PurchaseEvent to JSONObject
     *
     * @return JSONObject of PurchaseEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_purchase", purchaseData)
        put("affise_event_purchase_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "Purchase"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}