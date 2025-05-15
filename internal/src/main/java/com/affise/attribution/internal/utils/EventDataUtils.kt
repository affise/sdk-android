package com.affise.attribution.internal.utils

import com.affise.attribution.events.property.AffiseProperty
import com.affise.attribution.events.property.toAffiseEventProperty
import com.affise.attribution.events.subscription.SubscriptionParameters
import com.affise.attribution.events.subscription.SubscriptionSubType
import com.affise.attribution.parameters.Parameters

internal fun Map<*, *>.getEventName(): String? =
    this[Parameters.AFFISE_EVENT_NAME] as? String

internal fun Map<*, *>.getUserData(): String? =
    this[Parameters.AFFISE_EVENT_USER_DATA] as? String

internal fun Map<*, *>.getCategory(): String? =
    this[Parameters.AFFISE_EVENT_CATEGORY] as? String

internal fun Map<*, *>.getEventData(): Map<*, *>? =
    this[Parameters.AFFISE_EVENT_DATA] as? Map<*, *>

internal fun Map<*, *>.getEventParameters(): Map<*, *>? =
    this[Parameters.AFFISE_PARAMETERS] as? Map<*, *>

internal fun Map<*, *>.getTimeStamp(): Long? =
    getPropertyByKey<Number>(AffiseProperty.TIMESTAMP.type)
        ?.toLong()

internal fun Map<*, *>.getSubTypeName(): String? =
    getPropertyByName(SubscriptionParameters.AFFISE_SUBSCRIPTION_EVENT_TYPE_KEY)

internal fun Map<*, *>.getSubType(): SubscriptionSubType? = SubscriptionSubType.from(getSubTypeName())

internal fun <T> Map<*, *>.getPropertyByKey(key: String? = null): T? {
    return getPropertyByName(getEventName().toAffiseEventProperty(key))
}

@Suppress("UNCHECKED_CAST")
internal fun <T> Map<*, *>.getPropertyByName(name: String): T? {
    return getEventData()?.get(name) as? T
}

internal fun Map<*, *>.getPropertyByNameAny(name: String): Any? {
    return getEventData()?.get(name)
}