package com.affise.attribution.events

import com.affise.attribution.converter.Converter
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.parameters.Parameters
import com.affise.attribution.storages.EventsStorage
import org.json.JSONObject

/**
 * Events repository provide write, read and delete events.
 *
 * @property converterToBase64 convert string to encoding Base64 string
 * @property converterToSerializedEvent to convert Event to SerializedEvent
 * @property logsManager for error logging
 * @property eventsStorage storage of events
 */
internal class EventsRepositoryImpl(
    private val converterToBase64: Converter<String, String>,
    private val converterToSerializedEvent: Converter<Event, SerializedEvent>,
    private val logsManager: LogsManager,
    private val eventsStorage: EventsStorage
) : EventsRepository {

    /**
     * Has save events by [url] or not
     */
    override fun hasEvents(url: String) = eventsStorage.hasEvents(
        converterToBase64.convert(url)
    )

    /**
     * Store [event] by [urls]
     */
    override fun storeEvent(event: Event, urls: List<String>) {
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
     * Store web[event] by [urls]
     */
    override fun storeWebEvent(event: String, urls: List<String>) = try {
        //Create json
        val data = JSONObject(event)

        //Gei event id
        val id = data.getString(Parameters.AFFISE_EVENT_ID)

        //Create serialized event
        val serializedEvent = SerializedEvent(id, data)

        //For al urls
        urls.forEach {
            //Save event
            eventsStorage.saveEvent(
                converterToBase64.convert(it),
                serializedEvent
            )
        }
    } catch (throwable: Throwable) {
        //Log error
        logsManager.addUserError(throwable)
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