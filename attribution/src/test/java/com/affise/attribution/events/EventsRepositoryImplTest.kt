package com.affise.attribution.events

import com.affise.attribution.converter.Converter
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.storages.EventsStorage
import com.google.common.truth.Truth
import io.mockk.*
import org.json.JSONObject
import org.junit.Test

/**
 * Test for [EventsRepositoryImpl]
 */
class EventsRepositoryImplTest {

    private val url1 = "url1"
    private val url2 = "url2"

    private val url1Base64 = "url1Base64"
    private val url2Base64 = "url2Base64"

    private val test1EventId = "id1"
    private val test1EventJson = JSONObject("{\"test_key\":\"test_value\"}")
    private val test1Event: Event = mockk {
        every { serialize() } returns test1EventJson
    }
    private val serializedEvent1 = SerializedEvent(test1EventId, test1EventJson)

    private val webEvent = "{\"affise_event_id\":\"1\",\"test_key\":\"test_value\"}"
    private val webSerializedEventJson = "{\"affise_event_id\":\"1\",\"test_key\":\"test_value\"}"

    private val converter: Converter<Event, SerializedEvent> = mockk {
        every { convert(test1Event) } returns serializedEvent1
    }

    private val converterBase64: Converter<String, String> = mockk {
        every { convert(url1) } returns url1Base64
        every { convert(url2) } returns url2Base64
    }

    private val storage: EventsStorage = mockk {
        justRun { saveEvent(url1Base64, serializedEvent1) }
        justRun { saveEvent(url2Base64, serializedEvent1) }
        every { getEvents(url1Base64) } returns listOf(serializedEvent1)
        justRun { deleteEvent(url1Base64, listOf(test1EventId)) }
        justRun { clear() }
    }

    private val logsManager: LogsManager = mockk()

    private val repository: EventsRepositoryImpl by lazy {
        EventsRepositoryImpl(converterBase64, converter, logsManager, storage)
    }

    @Test
    fun `store event with 1 url`() {
        repository.storeEvent(test1Event, listOf(url1))

        verifyAll {
            converter.convert(test1Event)
            converterBase64.convert(url1)
            storage.saveEvent(url1Base64, serializedEvent1)
            logsManager wasNot Called
        }
    }

    @Test
    fun `store event with some url`() {
        repository.storeEvent(test1Event, listOf(url1, url2))

        verifyAll {
            converter.convert(test1Event)
            converterBase64.convert(url1)
            converterBase64.convert(url2)
            storage.saveEvent(url1Base64, serializedEvent1)
            storage.saveEvent(url2Base64, serializedEvent1)
            logsManager wasNot Called
        }
    }

    @Test
    fun `store web event with 1 url`() {
        val slot = slot<SerializedEvent>()
        justRun { storage.saveEvent(url1Base64, capture(slot)) }

        repository.storeWebEvent(webEvent, listOf(url1))

        Truth.assertThat(slot.isCaptured).isTrue()
        Truth.assertThat(slot.captured.id).isEqualTo("1")
        Truth.assertThat(slot.captured.data.toString()).isEqualTo(webSerializedEventJson)

        verifyAll {
            converter wasNot Called
            converterBase64.convert(url1)
            storage.saveEvent(url1Base64, capture(slot))
            logsManager wasNot Called
        }
    }

    @Test
    fun `store web event with some url`() {
        val slot = mutableListOf<SerializedEvent>()

        justRun { storage.saveEvent(url1Base64, capture(slot)) }
        justRun { storage.saveEvent(url2Base64, capture(slot)) }

        repository.storeWebEvent(webEvent, listOf(url1, url2))

        Truth.assertThat(slot.size).isEqualTo(2)
        Truth.assertThat(slot[0].id).isEqualTo("1")
        Truth.assertThat(slot[0].data.toString()).isEqualTo(webSerializedEventJson)
        Truth.assertThat(slot[1].id).isEqualTo("1")
        Truth.assertThat(slot[1].data.toString()).isEqualTo(webSerializedEventJson)

        verifyAll {
            converter wasNot Called
            converterBase64.convert(url1)
            converterBase64.convert(url2)
            storage.saveEvent(url1Base64, capture(slot))
            storage.saveEvent(url2Base64, capture(slot))
            logsManager wasNot Called
        }
    }

    @Test
    fun `get events`() {
        val events = repository.getEvents(url1)

        Truth.assertThat(events.size).isEqualTo(1)
        Truth.assertThat(events.first()).isEqualTo(serializedEvent1)

        verifyAll {
            converter wasNot Called
            converterBase64.convert(url1)
            storage.getEvents(url1Base64)
            logsManager wasNot Called
        }
    }

    @Test
    fun `delete events`() {
        repository.deleteEvent(listOf(test1EventId), url1)

        verifyAll {
            converter wasNot Called
            converterBase64.convert(url1)
            storage.deleteEvent(url1Base64, listOf(test1EventId))
            logsManager wasNot Called
        }
    }

    @Test
    fun `clear events`() {
        repository.clear()

        verifyAll {
            converter wasNot Called
            converterBase64 wasNot Called
            storage.clear()
            logsManager wasNot Called
        }
    }
}