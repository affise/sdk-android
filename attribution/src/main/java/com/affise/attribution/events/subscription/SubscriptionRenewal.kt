package com.affise.attribution.events.subscription

import org.json.JSONObject

/**
 * Event RenewedSubscription use [data] of event and [userData]
 */
class RenewedSubscriptionEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionParameters.AFFISE_SUBSCRIPTION_RENEWAL

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionParameters.AFFISE_SUB_RENEWED_SUBSCRIPTION
}