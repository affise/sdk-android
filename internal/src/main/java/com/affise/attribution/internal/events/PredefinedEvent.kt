package com.affise.attribution.internal.events

import com.affise.attribution.events.Event
import com.affise.attribution.events.custom.UserCustomEvent
import com.affise.attribution.internal.utils.getCategory
import com.affise.attribution.internal.utils.getTimeStamp
import com.affise.attribution.internal.utils.getUserData
import com.affise.attribution.utils.timestamp

internal object PredefinedEvent {

    fun create(name: String?, map: Map<*, *>): Event? {
        name ?: return null
        val timeStamp = map.getTimeStamp() ?: timestamp()
        val userData = map.getUserData()
        val category =  map.getCategory()

        return UserCustomEvent(
            eventName = name,
            userData = userData,
            timeStampMillis = timeStamp,
            category = category,
        )
    }
}