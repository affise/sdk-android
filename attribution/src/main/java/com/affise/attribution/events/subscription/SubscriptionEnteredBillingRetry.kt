package com.affise.attribution.events.subscription

import org.json.JSONObject

/**
 * Event TrialInRetry use [data] of event and [userData]
 */
class TrialInRetryEvent @JvmOverloads constructor(
    data: JSONObject? = null,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_ENTERED_BILLING_RETRY.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_TRIAL_IN_RETRY.typeName
}

/**
 * Event OfferInRetry use [data] of event and [userData]
 */
class OfferInRetryEvent @JvmOverloads constructor(
    data: JSONObject? = null,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_ENTERED_BILLING_RETRY.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_OFFER_IN_RETRY.typeName
}

/**
 * Event SubscriptionInRetry use [data] of event and [userData]
 */
class SubscriptionInRetryEvent @JvmOverloads constructor(
    data: JSONObject? = null,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_ENTERED_BILLING_RETRY.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_SUBSCRIPTION_IN_RETRY.typeName
}