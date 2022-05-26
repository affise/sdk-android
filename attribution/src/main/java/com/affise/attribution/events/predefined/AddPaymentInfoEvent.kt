package com.affise.attribution.events.predefined

import com.affise.attribution.events.NativeEvent
import org.json.JSONObject

/**
 * Event AddPaymentInfo
 *
 * @property paymentInfo the JSON Object describing the meaning of the event.
 * @property timeStampMillis the timestamp event in milliseconds.
 * @property userData any custom string data.
 */
class AddPaymentInfoEvent(
    private val paymentInfo: JSONObject,
    private val timeStampMillis: Long,
    private val userData: String? = null
) : NativeEvent() {

    /**
     * Serialize AddPaymentInfoEvent to JSONObject
     *
     * @return JSONObject of AddPaymentInfoEvent
     */
    override fun serialize() = JSONObject().apply {
        put("affise_event_add_payment_info", paymentInfo)
        put("affise_event_add_payment_info_timestamp", timeStampMillis)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "AddPaymentInfo"

    /**
     * User data
     *
     * @return userData
     */
    override fun getUserData() = userData
}