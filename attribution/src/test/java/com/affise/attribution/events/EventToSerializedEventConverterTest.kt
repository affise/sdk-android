package com.affise.attribution.events

import com.affise.attribution.utils.generateUUID
import com.affise.attribution.utils.timestamp
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * Test for [EventToSerializedEventConverter]
 */
class EventToSerializedEventConverterTest {
    @Before
    fun setUp() {
        mockkStatic(::timestamp)
        mockkStatic(::generateUUID)
    }

    @After
    fun tearDown() {
        unmockkStatic(::timestamp)
        unmockkStatic(::generateUUID)
    }

    @Test
    fun `verify convert`() {
        val uuid = UUID.fromString("be07d122-3f3c-11ec-9bbc-0242ac130002")
        every {
            generateUUID()
        } returns uuid

        every {
            timestamp()
        } returns 1636229513985

        val converter = EventToSerializedEventConverter()

        val event: Event = mockk {
            every { serialize() } returns JSONObject()
            every { getName() } returns "name"
            every { getCategory() } returns "category"
            every { isFirstForUser() } returns false
            every { getUserData() } returns "user-data"
            every { getPredefinedParameters() } returns mapOf()
        }
        val actual = converter.convert(event)

        val expected = javaClass.classLoader.getResourceAsStream("serialized_event.json").use {
            it?.bufferedReader()?.readText()
        }
        Truth.assertThat(actual.id).isEqualTo(uuid.toString())
        Truth.assertThat(actual.data.toString()).isEqualTo(expected)
    }
}