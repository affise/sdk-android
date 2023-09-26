package com.affise.attribution.events.subscription

import org.json.JSONObject

/**
 * Event ConvertedTrial use [data] of event and [userData]
 */
class ConvertedTrialEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_FIRST_CONVERSION.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_CONVERTED_TRIAL.typeName
}

/**
 * Event ConvertedOffer use [data] of event and [userData]
 */
class ConvertedOfferEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_FIRST_CONVERSION.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_CONVERTED_OFFER.typeName
}