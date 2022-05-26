package com.affise.attribution.events

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.affise.attribution.converter.Converter
import com.affise.attribution.events.predefined.GDPREvent
import org.json.JSONObject

/**
 * Stores [GDPREvent] indicating is forget me event should be sent
 */
internal class GDPREventRepository(
    private val sharedPreferences: SharedPreferences,
    private val eventToSerializedEventConverter: Converter<Event, SerializedEvent>
) {
    @SuppressLint("ApplySharedPref")
    fun setEvent(event: GDPREvent) {
        sharedPreferences.edit().apply {
            val serialized = eventToSerializedEventConverter.convert(event)
            putString(GDPR_EVENT_ID_KEY, serialized.id)
            putString(GDPR_EVENT_CONTENT_KEY, serialized.data.toString())
        }.commit()
    }

    fun getEvent(): SerializedEvent? {
        val id = sharedPreferences.getString(GDPR_EVENT_ID_KEY, null) ?: return null
        val content = sharedPreferences.getString(GDPR_EVENT_CONTENT_KEY, null) ?: return null
        return SerializedEvent(id, JSONObject(content))
    }

    @SuppressLint("ApplySharedPref")
    fun clear() {
        sharedPreferences.edit().apply {
            remove(GDPR_EVENT_ID_KEY)
            remove(GDPR_EVENT_CONTENT_KEY)
        }.commit()
    }

    companion object {
        private const val GDPR_EVENT_ID_KEY = "com.affise.attribution.usecase.GDPR_EVENT.id"
        private const val GDPR_EVENT_CONTENT_KEY = "com.affise.attribution.usecase.GDPR_EVENT.content"
    }
}