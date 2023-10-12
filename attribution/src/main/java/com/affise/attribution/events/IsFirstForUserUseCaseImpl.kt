package com.affise.attribution.events

import com.affise.attribution.events.subscription.BaseSubscriptionEvent
import com.affise.attribution.events.subscription.SubscriptionParameters
import com.affise.attribution.parameters.Parameters
import com.affise.attribution.storages.IsFirstForUserStorage
import org.json.JSONObject

/**
 * Event use case for IsFirstForUser
 *
 * @property isFirstForUserStorage storage of already send events
 */
internal class IsFirstForUserUseCaseImpl(
    private val isFirstForUserStorage: IsFirstForUserStorage
) : IsFirstForUserUseCase {

    /**
     * Cache of already send events
     */
    private var cache: MutableList<String> = mutableListOf()

    init {
        // Init cache of already send events
        cache = isFirstForUserStorage.getEventsNames().toMutableList()
    }

    /**
     * Update IsFirstForUser
     */
    override fun updateEvent(event: Event) {
        val eventClass = if (event is BaseSubscriptionEvent) {
            event.subtype
        }else {
            event.getName()
        }
        if (cache.contains(eventClass)) {
            event.setFirstForUser(false)
        } else {
            cache.add(eventClass)
            isFirstForUserStorage.add(eventClass)
            event.setFirstForUser(true)
        }
    }

    /**
     * Update IsFirstForUser
     */
    override fun updateWebEvent(event: String): String {
        val eventJson = try {
            JSONObject(event)
        } catch (_: Exception) {
            JSONObject()
        }

        val subType = eventJson
            .optJSONObject(Parameters.AFFISE_EVENT_DATA)
            ?.optString(SubscriptionParameters.AFFISE_SUBSCRIPTION_EVENT_TYPE_KEY)

        val eventClass = if (!subType.isNullOrBlank()) {
            subType
        } else {
            eventJson.optString(Parameters.AFFISE_EVENT_NAME)
        }

        eventClass ?: return event

        if (cache.contains(eventClass)) {
            eventJson.putOpt(Parameters.AFFISE_EVENT_FIRST_FOR_USER, false)
        } else {
            cache.add(eventClass)
            isFirstForUserStorage.add(eventClass)
            eventJson.putOpt(Parameters.AFFISE_EVENT_FIRST_FOR_USER, true)
        }
        return eventJson.toString()
    }
}