package com.affise.attribution.internal

import com.affise.attribution.converter.Converter
import com.affise.attribution.events.SerializedEvent
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.storages.InternalEventsStorage

/**
 * Internal events repository provide write, read and delete events.
 *
 * @property converterToBase64 convert string to encoding Base64 string
 * @property converterToSerializedEvent to convert SdkEvent to SerializedEvent
 * @property logsManager for error logging
 * @property eventsStorage storage of events
 */
internal class InternalEventsRepositoryImpl(
    private val converterToBase64: Converter<String, String>,
    private val converterToSerializedEvent: Converter<InternalEvent, SerializedEvent>,
    private val logsManager: LogsManager,
    private val eventsStorage: InternalEventsStorage
) : InternalEventsRepository {

    /**
     * Has save events by [url] or not
     */
    override fun hasEvents(url: String) = eventsStorage.hasEvents(
        converterToBase64.convert(url)
    )

    /**
     * Store [event] by [urls]
     */
    override fun storeEvent(event: InternalEvent, urls: List<String>) {
        //For al urls
        urls.forEach {
            //Save event
            eventsStorage.saveEvent(
                converterToBase64.convert(it),
                converterToSerializedEvent.convert(event)
            )
        }
    }

    /**
     * Get serialized events by [url]
     *
     * @return list of serialized events
     */
    override fun getEvents(url: String): List<SerializedEvent> = eventsStorage.getEvents(
        converterToBase64.convert(url)
    )

    /**
     * Delete event for [url] by [ids]
     */
    override fun deleteEvent(ids: List<String>, url: String) {
        eventsStorage.deleteEvent(
            converterToBase64.convert(url),
            ids
        )
    }

    /**
     * Removes all events
     */
    override fun clear() {
        eventsStorage.clear()
    }
}