package com.affise.attribution.internal

import com.affise.attribution.events.SerializedEvent

/**
 * Internal Events repository interface
 */
internal interface InternalEventsRepository {

    /**
     * Has save events by [url] or not
     */
    fun hasEvents(url: String): Boolean

    /**
     * Event recording for each url
     */
    fun storeEvent(event: InternalEvent, urls: List<String>)

    /**
     * Get event in dir
     */
    fun getEvents(url: String): List<SerializedEvent>

    /**
     * Delete events in dir
     */
    fun deleteEvent(ids: List<String>, url: String)

    /**
     * Removes all events
     */
    fun clear()
}