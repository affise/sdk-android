package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event AddToCart
 *
 * @property addToCartObject the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class AddToCartEvent(
    private val addToCartObject: JSONObject?,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize AddToCartEvent to JSONObject
     *
     * @return JSONObject of AddToCartEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_add_to_cart", addToCartObject)
        put("affise_event_add_to_cart_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "AddToCart"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}