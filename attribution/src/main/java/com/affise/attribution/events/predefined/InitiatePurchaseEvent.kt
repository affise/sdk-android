package com.affise.attribution.events.predefined

import com.affise.attribution.events.EventName
import com.affise.attribution.events.NativeEvent
import com.affise.attribution.utils.timestamp
import org.json.JSONObject

/**
 * Event InitiatePurchase
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
class InitiatePurchaseEvent(
    private val userData: String? = null,
    private val timeStampMillis: Long = timestamp(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {
    /**
     * Event InitiatePurchase
     *
     * @property purchaseData the JSON Object describing the meaning of the event.
     * @property timeStampMillis the timestamp event in milliseconds.
     * @property userData any custom string data.
     */
    @Deprecated(
        message = "This constructor will be removed in future",
        replaceWith = ReplaceWith("InitiatePurchaseEvent(userData, timeStampMillis)"),
        level = DeprecationLevel.WARNING
    )
    constructor(
        purchaseData: JSONObject = JSONObject(),
        timeStampMillis: Long = timestamp(),
        userData: String? = null,
    ) : this(
        userData = userData,
        timeStampMillis = timeStampMillis,
    ) {
        anyData = purchaseData
    }
    override fun getName(): String = EventName.INITIATE_PURCHASE.eventName
}