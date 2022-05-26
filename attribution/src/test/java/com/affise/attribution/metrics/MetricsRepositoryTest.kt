package com.affise.attribution.metrics

import com.affise.attribution.converter.Converter
import com.affise.attribution.events.Event
import com.affise.attribution.events.SerializedEvent
import com.affise.attribution.storages.MetricsStorage
import com.google.common.truth.Truth
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * Test for [MetricsRepositoryImpl]
 */
class MetricsRepositoryTest {

    private val url1 = "url1"
    private val url2 = "url2"
    private val url1Base64 = "url1Base64"
    private val url2Base64 = "url2Base64"

    private val currentDayTime = 1L
    private val currentDayTimeString = "1"
    private val currentDayNameDir = "currentDayDir"

    private val metricsDataTest1 = MetricsData().apply {
        activityName = "activityName1"
        openTime = 1
        clicksData = mutableListOf(
            MetricsClickData().apply {
                name = "name1"
                count = 1
            }
        )
    }

    private val metricsDataTest2 = MetricsData().apply {
        activityName = "activityName1"
        openTime = 1
        clicksData = mutableListOf(
            MetricsClickData().apply {
                name = "name2"
                count = 1
            }
        )
    }

    private val metricsDataTest3 = MetricsData().apply {
        activityName = "activityName2"
        openTime = 1
        clicksData = mutableListOf(
            MetricsClickData().apply {
                name = "name1"
                count = 1
            }
        )
    }

    private val testEvent: MetricsEvent = mockk {
        every { data } returns mutableListOf()
    }
    private val testEvent2: MetricsEvent = mockk {
        every { data } returns mutableListOf()
    }

    private val testSerializedEvent: SerializedEvent = mockk()

    private val converterToBase64: Converter<String, String> = mockk {
        every { convert(url1) } returns url1Base64
        every { convert(url2) } returns url2Base64
        every { convert(currentDayTimeString) } returns currentDayNameDir
    }

    private val converterToSerializedEvent: Converter<Event, SerializedEvent> = mockk {
        every { convert(testEvent) } returns testSerializedEvent
    }

    private val metricsStorage: MetricsStorage = mockk {
        every { getMetricsEvents(url1Base64, currentDayNameDir) } returns listOf(testEvent)
        every { getMetricsEvents(url2Base64, currentDayNameDir) } returns listOf(testEvent2)
        every { getMetricsEvent(url1Base64, currentDayNameDir) } returns testEvent
        every { getMetricsEvent(url2Base64, currentDayNameDir) } returns testEvent2
        justRun { saveMetricsEvent(url1Base64, currentDayNameDir, testEvent) }
        justRun { saveMetricsEvent(url2Base64, currentDayNameDir, testEvent2) }
        justRun { deleteMetrics(url1Base64, currentDayNameDir) }
        justRun { clear() }
    }

    private val repository = MetricsRepositoryImpl(
        converterToBase64, converterToSerializedEvent, metricsStorage
    )

    @Before
    fun setUp() {
        mockkStatic(Calendar::class)
        justRun { Calendar.getInstance().set(Calendar.MILLISECOND, 0) }
        justRun { Calendar.getInstance().set(Calendar.SECOND, 0) }
        justRun { Calendar.getInstance().set(Calendar.MINUTE, 0) }
        justRun { Calendar.getInstance().set(Calendar.HOUR_OF_DAY, 0) }
        every { Calendar.getInstance().timeInMillis } returns currentDayTime
    }

    @After
    fun tearDown() {
        unmockkStatic(Calendar::class)
    }

    @Test
    fun getOldEvents() {
        val events = repository.getMetrics(url1)

        Truth.assertThat(events.size).isEqualTo(1)
        Truth.assertThat(events.first()).isEqualTo(testSerializedEvent)

        verifyAll {
            testEvent wasNot Called
            converterToBase64.convert(url1)
            converterToBase64.convert(currentDayTimeString)
            converterToSerializedEvent.convert(testEvent)
            metricsStorage.getMetricsEvents(url1Base64, currentDayNameDir)
        }
    }

    @Test
    fun `add metrics data`() {
        repository.addMetricsData(metricsDataTest1, listOf(url1))

        verifyAll {
            testEvent.data
            converterToBase64.convert(url1)
            converterToBase64.convert(currentDayTimeString)
            converterToSerializedEvent wasNot Called
            metricsStorage.getMetricsEvent(url1Base64, currentDayNameDir)
            metricsStorage.saveMetricsEvent(url1Base64, currentDayNameDir, testEvent)
        }
    }

    @Test
    fun `add metrics data with many urls`() {
        repository.addMetricsData(metricsDataTest1, listOf(url1, url2))

        verifyAll {
            testEvent.data
            converterToBase64.convert(url1)
            converterToBase64.convert(url2)
            converterToBase64.convert(currentDayTimeString)
            converterToSerializedEvent wasNot Called
            metricsStorage.getMetricsEvent(url1Base64, currentDayNameDir)
            metricsStorage.getMetricsEvent(url2Base64, currentDayNameDir)
            metricsStorage.getMetricsEvent(url2Base64, currentDayNameDir)
            metricsStorage.saveMetricsEvent(url1Base64, currentDayNameDir, testEvent)
            metricsStorage.saveMetricsEvent(url2Base64, currentDayNameDir, testEvent2)
        }
    }

    @Test
    fun `add metrics data with many events`() {
        repository.addMetricsData(metricsDataTest1, listOf(url1))
        repository.addMetricsData(metricsDataTest1, listOf(url1))
        repository.addMetricsData(metricsDataTest2, listOf(url1))
        repository.addMetricsData(metricsDataTest3, listOf(url1))

        val event = metricsStorage.getMetricsEvent(url1Base64, currentDayNameDir)!!

        val eventData = event.data

        Truth.assertThat(eventData.size).isEqualTo(2)

        val activities = eventData.map { it.activityName }

        val openTimes = eventData.map { it.openTime }

        val clicksData = eventData.mapNotNull { it.clicksData }
            .flatten()

        Truth.assertThat(clicksData.size).isEqualTo(3)

        val clicksDataNames = clicksData.map { it.name }

        val clicksDataCounts = clicksData.map { it.count }

        Truth.assertThat(activities[0])
            .isEqualTo("activityName1")//metricsDataTest1 and metricsDataTest2
        Truth.assertThat(activities[1]).isEqualTo("activityName2")//metricsDataTest3
        Truth.assertThat(openTimes[0])
            .isEqualTo(3)//metricsDataTest1, metricsDataTest1 and metricsDataTest2
        Truth.assertThat(openTimes[1]).isEqualTo(1)//metricsDataTest3

        Truth.assertThat(clicksData.size).isEqualTo(3)
        Truth.assertThat(clicksDataNames[0]).isEqualTo("name1")//metricsDataTest1
        Truth.assertThat(clicksDataNames[1]).isEqualTo("name2")//metricsDataTest2
        Truth.assertThat(clicksDataNames[2]).isEqualTo("name1")//metricsDataTest3
        Truth.assertThat(clicksDataCounts[0]).isEqualTo(2)//metricsDataTest1 and metricsDataTest1
        Truth.assertThat(clicksDataCounts[1]).isEqualTo(1)//metricsDataTest2
        Truth.assertThat(clicksDataCounts[2]).isEqualTo(1)//metricsDataTest3

        verifyAll {
            converterToBase64.convert(url1)
            converterToBase64.convert(currentDayTime.toString())
        }
    }

    @Test
    fun clear() {
        repository.clear()

        verifyAll {
            converterToBase64 wasNot Called
            metricsStorage.clear()
        }
    }

    @Test
    fun deleteMetrics() {
        repository.deleteMetrics(url1)

        verifyAll {
            converterToBase64.convert(url1)
            converterToBase64.convert(currentDayTimeString)
            metricsStorage.deleteMetrics(url1Base64, currentDayNameDir)
        }
    }
}