package com.affise.attribution.events.subscription

import org.json.JSONObject

/**
 * Event ConvertedTrialFromRetry use [data] of event and [userData]
 */
class ConvertedTrialFromRetryEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionParameters.AFFISE_SUBSCRIPTION_RENEWAL_FROM_BILLING_RETRY

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionParameters.AFFISE_SUB_CONVERTED_TRIAL_FROM_RETRY
}

/**
 * Event ConvertedOfferFromRetry use [data] of event and [userData]
 */
class ConvertedOfferFromRetryEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionParameters.AFFISE_SUBSCRIPTION_RENEWAL_FROM_BILLING_RETRY

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionParameters.AFFISE_SUB_CONVERTED_OFFER_FROM_RETRY
}

/**
 * Event RenewedSubscriptionFromRetry use [data] of event and [userData]
 */
class RenewedSubscriptionFromRetryEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionParameters.AFFISE_SUBSCRIPTION_RENEWAL_FROM_BILLING_RETRY

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionParameters.AFFISE_SUB_RENEWED_SUBSCRIPTION_FROM_RETRY
}