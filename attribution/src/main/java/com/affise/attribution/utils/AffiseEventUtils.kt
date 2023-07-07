package com.affise.attribution.utils

import com.affise.attribution.events.NativeEvent

fun toEventName(event: NativeEvent): String {
    return toEventName(event.javaClass)
}

fun toEventName(eventClass: Class<out NativeEvent>): String {
    return eventClass.simpleName.removeSuffix(EVENT_SUFFIX)
}


fun toAffiseEventClass(eventName: String?): Class<out NativeEvent>? =
    eventName?.let {
        try {
            @Suppress("UNCHECKED_CAST")
            Class.forName("$PREDEFINED_PACKAGE.${it}$EVENT_SUFFIX") as? Class<out NativeEvent>
        } catch (_: Exception) {
            null
        }
    }

fun toAffiseSubscriptionEventClass(eventName: String?): Class<out NativeEvent>? =
    eventName?.let {
        try {
            @Suppress("UNCHECKED_CAST")
            Class.forName("$SUBSCRIPTION_PACKAGE.${it}$EVENT_SUFFIX") as? Class<out NativeEvent>
        } catch (_: Exception) {
            null
        }
    }

private const val EVENTS_PACKAGE = "com.affise.attribution.events"
private const val PREDEFINED_PACKAGE = "$EVENTS_PACKAGE.predefined"
private const val SUBSCRIPTION_PACKAGE = "$EVENTS_PACKAGE.subscription"
private const val EVENT_SUFFIX = "Event"
