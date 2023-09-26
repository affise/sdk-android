package com.affise.attribution.events.subscription

import org.json.JSONObject

/**
 * Event ReactivatedSubscription use [data] of event and [userData]
 */
class ReactivatedSubscriptionEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_REACTIVATION.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_REACTIVATED_SUBSCRIPTION.typeName
}