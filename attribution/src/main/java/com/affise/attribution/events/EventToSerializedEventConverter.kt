package com.affise.attribution.events

import com.affise.attribution.converter.Converter
import com.affise.attribution.parameters.Parameters
import org.json.JSONObject
import java.util.Calendar
import java.util.UUID

/**
 * Converter Event to SerializedEvent
 */
class EventToSerializedEventConverter : Converter<Event, SerializedEvent> {

    /**
     * Convert [from] Event to SerializedEvent
     */
    override fun convert(from: Event): SerializedEvent {
        //Generate id
        val id = UUID.randomUUID().toString()

        //Create JSONObject
        val json = JSONObject().apply {
            //Add Id
            put(Parameters.AFFISE_EVENT_ID, id)

            //Add name
            put(Parameters.AFFISE_EVENT_NAME, from.getName())

            //Add category
            put(Parameters.AFFISE_EVENT_CATEGORY, from.getCategory())

            //Add timestamp
            put(Parameters.AFFISE_EVENT_TIMESTAMP, Calendar.getInstance().time.time)

            //Add is first for user Or not
            put(Parameters.AFFISE_EVENT_FIRST_FOR_USER, from.isFirstForUser())

            //Add user data
            put(Parameters.AFFISE_EVENT_USER_DATA, from.getUserData())

            //Add event data
            put(Parameters.AFFISE_EVENT_DATA, from.serialize())
            //Add predefined parameters
            put(
                Parameters.AFFISE_PARAMETERS,
                from.getPredefinedParameters()
                    .mapKeys { it.key.value }
                    .let(::JSONObject)
            )
        }

        //Create SerializedEvent
        return SerializedEvent(id, json)
    }
}