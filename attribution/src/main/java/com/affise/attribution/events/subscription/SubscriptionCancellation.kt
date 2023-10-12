package com.affise.attribution.events.subscription

import org.json.JSONObject

/**
 * Event FailedTrial use [data] of event and [userData]
 */
class FailedTrialEvent @JvmOverloads constructor(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_CANCELLATION.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_FAILED_TRIAL.typeName
}

/**
 * Event FailedOfferise use [data] of event and [userData]
 */
class FailedOfferiseEvent @JvmOverloads constructor(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_CANCELLATION.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_FAILED_OFFERISE.typeName
}

/**
 * Event FailedSubscription use [data] of event and [userData]
 */
class FailedSubscriptionEvent @JvmOverloads constructor(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_CANCELLATION.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_FAILED_SUBSCRIPTION.typeName
}

/**
 * Event FailedTrialFromRetry use [data] of event and [userData]
 */
class FailedTrialFromRetryEvent @JvmOverloads constructor(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_CANCELLATION.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_FAILED_TRIAL_FROM_RETRY.typeName
}

/**
 * Event FailedOfferFromRetry use [data] of event and [userData]
 */
class FailedOfferFromRetryEvent @JvmOverloads constructor(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_CANCELLATION.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_FAILED_OFFER_FROM_RETRY.typeName
}

/**
 * Event FailedSubscriptionFromRetry use [data] of event and [userData]
 */
class FailedSubscriptionFromRetryEvent @JvmOverloads constructor(
    data: JSONObject,
    userData: String? = null
) : BaseSubscriptionEvent(data, userData) {

    /**
     * Type of event
     */
    override val type = SubscriptionEventName.AFFISE_SUBSCRIPTION_CANCELLATION.eventName

    /**
     * Subtype of event
     */
    override val subtype = SubscriptionSubType.AFFISE_SUB_FAILED_SUBSCRIPTION_FROM_RETRY.typeName
}