package com.affise.attribution.events.subscription

import org.json.JSONObject

/**
 * Event InitialSubscription use [data] of event and [userData]
 */
class InitialSubscriptionEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionParameters.AFFISE_SUBSCRIPTION_ACTIVATION

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionParameters.AFFISE_SUB_INITIAL_SUBSCRIPTION
}

/**
 * Event InitialTrial use [data] of event and [userData]
 */
class InitialTrialEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionParameters.AFFISE_SUBSCRIPTION_ACTIVATION

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionParameters.AFFISE_SUB_INITIAL_TRIAL
}

/**
 * Event of InitialOffer use [data] of event and [userData]
 */
class InitialOfferEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionParameters.AFFISE_SUBSCRIPTION_ACTIVATION

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionParameters.AFFISE_SUB_INITIAL_OFFER
}