package com.affise.attribution.events.subscription

import org.json.JSONObject

/**
 * Event FailedTrial use [data] of event and [userData]
 */
class FailedTrialEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionParameters.AFFISE_SUBSCRIPTION_CANCELLATION

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionParameters.AFFISE_SUB_FAILED_TRIAL
}

/**
 * Event FailedOfferise use [data] of event and [userData]
 */
class FailedOfferiseEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionParameters.AFFISE_SUBSCRIPTION_CANCELLATION

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionParameters.AFFISE_SUB_FAILED_OFFERISE
}

/**
 * Event FailedSubscription use [data] of event and [userData]
 */
class FailedSubscriptionEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionParameters.AFFISE_SUBSCRIPTION_CANCELLATION

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionParameters.AFFISE_SUB_FAILED_SUBSCRIPTION
}

/**
 * Event FailedTrialFromRetry use [data] of event and [userData]
 */
class FailedTrialFromRetryEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionParameters.AFFISE_SUBSCRIPTION_CANCELLATION

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionParameters.AFFISE_SUB_FAILED_TRIAL_FROM_RETRY
}

/**
 * Event FailedOfferFromRetry use [data] of event and [userData]
 */
class FailedOfferFromRetryEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionParameters.AFFISE_SUBSCRIPTION_CANCELLATION

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionParameters.AFFISE_SUB_FAILED_OFFER_FROM_RETRY
}

/**
 * Event FailedSubscriptionFromRetry use [data] of event and [userData]
 */
class FailedSubscriptionFromRetryEvent(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionParameters.AFFISE_SUBSCRIPTION_CANCELLATION

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionParameters.AFFISE_SUB_FAILED_SUBSCRIPTION_FROM_RETRY
}