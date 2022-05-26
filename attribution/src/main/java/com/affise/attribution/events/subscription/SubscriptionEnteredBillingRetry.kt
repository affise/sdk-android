package com.affise.attribution.events.subscription

import org.json.JSONObject

/**
 * Event TrialInRetry use [data] of event and [userData]
 */
class TrialInRetryEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionParameters.AFFISE_SUBSCRIPTION_ENTERED_BILLING_RETRY

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionParameters.AFFISE_SUB_TRIAL_IN_RETRY
}

/**
 * Event OfferInRetry use [data] of event and [userData]
 */
class OfferInRetryEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionParameters.AFFISE_SUBSCRIPTION_ENTERED_BILLING_RETRY

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionParameters.AFFISE_SUB_OFFER_IN_RETRY
}

/**
 * Event SubscriptionInRetry use [data] of event and [userData]
 */
class SubscriptionInRetryEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionParameters.AFFISE_SUBSCRIPTION_ENTERED_BILLING_RETRY

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionParameters.AFFISE_SUB_SUBSCRIPTION_IN_RETRY
}