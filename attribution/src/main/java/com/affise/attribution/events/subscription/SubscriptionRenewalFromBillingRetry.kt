package com.affise.attribution.events.subscription

import org.json.JSONObject

/**
 * Event ConvertedTrialFromRetry use [data] of event and [userData]
 */
class ConvertedTrialFromRetryEvent @JvmOverloads constructor(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_RENEWAL_FROM_BILLING_RETRY.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_CONVERTED_TRIAL_FROM_RETRY.typeName
}

/**
 * Event ConvertedOfferFromRetry use [data] of event and [userData]
 */
class ConvertedOfferFromRetryEvent @JvmOverloads constructor(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_RENEWAL_FROM_BILLING_RETRY.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_CONVERTED_OFFER_FROM_RETRY.typeName
}

/**
 * Event RenewedSubscriptionFromRetry use [data] of event and [userData]
 */
class RenewedSubscriptionFromRetryEvent @JvmOverloads constructor(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_RENEWAL_FROM_BILLING_RETRY.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_RENEWED_SUBSCRIPTION_FROM_RETRY.typeName
}