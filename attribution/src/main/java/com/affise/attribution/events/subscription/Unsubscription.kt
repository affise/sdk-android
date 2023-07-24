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
    override val type = SubscriptionEventName.AFFISE_UNSUBSCRIPTION

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_UNSUBSCRIPTION
}