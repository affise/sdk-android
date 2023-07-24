package com.affise.attribution.events.subscription

import com.affise.attribution.events.NativeEvent
import com.affise.attribution.events.property.AffisePropertyBuilder
import com.affise.attribution.utils.timestamp
import org.json.JSONObject

/**
 * Base Event of Subscription use [data] of event and [userData]
 */
abstract class BaseSubscriptionEvent(
    private val data: JSONObject,
    private val userData: String? = null,
) : NativeEvent(timeStampMillis = timestamp(), userData = userData) {

    /**
     * Type of subscription
     *
     */
    abstract val type: SubscriptionEventName

    /**
     * Subtype of subscription
     */
    abstract val subtype: SubscriptionSubType

    override fun serializeBuilder(): AffisePropertyBuilder = super.serializeBuilder()
        .addRaw(SubscriptionParameters.AFFISE_SUBSCRIPTION_EVENT_TYPE_KEY, subtype.typeName).apply {
            data.keys().forEach { key ->
                addRaw(key, data.get(key))
            }
        }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = type.eventName
}