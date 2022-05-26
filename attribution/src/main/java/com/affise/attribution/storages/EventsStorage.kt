package com.affise.attribution.storages

import com.affise.attribution.events.SerializedEvent

internal interface EventsStorage {
    fun saveEvent(key: String, event: SerializedEvent)
    fun getEvents(key: String?): List<SerializedEvent>
    fun deleteEvent(key: String?, ids: List<String>)
    fun clear()
}