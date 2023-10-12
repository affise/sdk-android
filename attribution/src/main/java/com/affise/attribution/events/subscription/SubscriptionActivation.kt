package com.affise.attribution.events.subscription

import org.json.JSONObject

/**
 * Event InitialSubscription use [data] of event and [userData]
 */
class InitialSubscriptionEvent @JvmOverloads constructor(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_ACTIVATION.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_INITIAL_SUBSCRIPTION.typeName
}

/**
 * Event InitialTrial use [data] of event and [userData]
 */
class InitialTrialEvent @JvmOverloads constructor(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_ACTIVATION.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_INITIAL_TRIAL.typeName
}

/**
 * Event of InitialOffer use [data] of event and [userData]
 */
class InitialOfferEvent @JvmOverloads constructor(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_ACTIVATION.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_INITIAL_OFFER.typeName
}