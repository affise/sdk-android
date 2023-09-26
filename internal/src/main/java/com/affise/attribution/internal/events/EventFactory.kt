package com.affise.attribution.internal.events

import android.util.Log
import com.affise.attribution.events.Event
import com.affise.attribution.internal.utils.getEventName
import com.affise.attribution.internal.utils.getEventParameters


internal class EventFactory {

    fun create(eventData: Map<*, *>): Event? {
        val name = eventData.getEventName()

        val event = when {
            SubscriptionEvent.isSubscriptionEvent(eventData) ->
                SubscriptionEvent.create(name, eventData)

            else ->
                PredefinedEvent.create(name, eventData)
        }

        if (event?.getName() != name) {
            Log.d(
                this.javaClass.simpleName,
                "Wrong event type [${event?.getName()}] expected [$name]"
            )
            return null
        }

        EventParameters.addParameters(event, eventData.getEventParameters())
        return event
    }
}