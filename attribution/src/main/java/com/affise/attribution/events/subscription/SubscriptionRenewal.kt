package com.affise.attribution.events.subscription

import org.json.JSONObject

/**
 * Event RenewedSubscription use [data] of event and [userData]
 */
class RenewedSubscriptionEvent @JvmOverloads constructor(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_RENEWAL.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_RENEWED_SUBSCRIPTION.typeName
}