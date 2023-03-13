package com.affise.attribution.events.subscription

import org.json.JSONObject

/**
 * Event Unsubscription use [data] of event and [userData]
 */
class UnsubscriptionEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionParameters.AFFISE_UNSUBSCRIPTION

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionParameters.AFFISE_SUB_UNSUBSCRIPTION
}