package com.affise.attribution.events

/**
 * Events repository interface
 */
internal interface EventsRepository {

    /**
     * Has save events by [url] or not
     */
    fun hasEvents(url: String): Boolean

    /**
     * Event recording for each url
     */
    fun storeEvent(event: Event, urls: List<String>)

    /**
     * Web Event recording for each url
     */
    fun storeWebEvent(event: String, urls: List<String>)

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