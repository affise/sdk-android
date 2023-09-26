package com.affise.attribution.internal.events

import com.affise.attribution.events.Event
import com.affise.attribution.events.custom.UserCustomSubscriptionEvent
import com.affise.attribution.internal.utils.getEventData
import com.affise.attribution.internal.utils.getSubTypeName
import com.affise.attribution.internal.utils.getUserData
import org.json.JSONObject

internal object SubscriptionEvent {

    fun isSubscriptionEvent(map: Map<*, *>): Boolean {
        return map.getSubTypeName() != null
    }

    fun create(type: String?, map: Map<*, *>): Event? {
        type ?: return null
        val subType = map.getSubTypeName()
        subType ?: return null
        val userData = map.getUserData()
        val eventData = map.getEventData() ?: mapOf<Any, Any?>()
        val data = JSONObject(eventData.toMutableMap())

        return UserCustomSubscriptionEvent(
            type = type,
            subtype = subType,
            data = data,
            userData = userData
        )
    }
}