package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event InitiatePurchase
 *
 * @property purchaseData the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class InitiatePurchaseEvent(
    private val purchaseData: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize InitiatePurchaseEvent to JSONObject
     *
     * @return JSONObject of InitiatePurchaseEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_initiate_purchase", purchaseData)
        put("affise_event_initiate_purchase_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "InitiatePurchase"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}