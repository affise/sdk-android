package com.affise.attribution.internal

import com.affise.attribution.converter.Converter
import com.affise.attribution.events.SerializedEvent
import com.affise.attribution.utils.generateUUID
import org.json.JSONObject

/**
 * Converter Internal Event to SerializedEvent
 */
class InternalEventToSerializedEventConverter : Converter<InternalEvent, SerializedEvent> {

    /**
     * Convert [from] Event to SerializedEvent
     */
    override fun convert(from: InternalEvent): SerializedEvent {
        //Generate id
        val id = generateUUID().toString()

        //Create JSONObject
        val json = JSONObject().apply {
            //Add Id
            put(InternalParameters.AFFISE_INTERNAL_EVENT_ID, id)

            //Add name
            put(InternalParameters.AFFISE_INTERNAL_EVENT_NAME, from.getName())

            //Add timestamp
            put(InternalParameters.AFFISE_INTERNAL_EVENT_TIMESTAMP, from.getTimestamp())

            //Add event data
            put(InternalParameters.AFFISE_INTERNAL_EVENT_DATA, from.serialize())
        }

        //Create SerializedEvent
        return SerializedEvent(id, json)
    }
}