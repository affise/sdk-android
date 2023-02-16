package com.affise.attribution.events

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
        val eventClass = event.javaClass.simpleName
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
        val eventJson = JSONObject(event)
        val eventClass = eventJson.classOfEvent()?.simpleName ?: return event
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