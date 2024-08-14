package com.affise.attribution.module.subscription.events

import com.affise.attribution.events.NativeEvent
import com.affise.attribution.utils.timestamp


/**
 * Purchase event.
 *
 * @property userData any custom string data.
 * @property timeStampMillis the timestamp event in milliseconds.
 */
internal class AfPurchaseEvent @JvmOverloads constructor(
    private val userData: String? = null,
    private val timeStampMillis: Long = timestamp(),
) : NativeEvent(
    userData = userData,
    timeStampMillis = timeStampMillis
) {

    override fun getName(): String = SubscriptionEventName.AF_PURCHASE.eventName
}