package com.affise.attribution.usecase

import com.affise.attribution.events.EventsRepository
import com.affise.attribution.events.SerializedEvent
import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.internal.InternalEventsRepository
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.logs.LogsRepository
import com.affise.attribution.logs.SerializedLog
import com.affise.attribution.metrics.MetricsRepository
import com.affise.attribution.network.CloudConfig
import com.affise.attribution.network.CloudRepository
import com.affise.attribution.network.entity.PostBackModel
import com.affise.attribution.parameters.factory.PostBackModelFactory
import com.google.common.truth.Truth
import com.google.common.util.concurrent.MoreExecutors
import io.mockk.Called
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verifyAll
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

@Ignore("TODO: fix in https://affise.atlassian.net/browse/AA-154 ")
class SendDataToServerUseCaseTest {

    private val url1 = "https://url1"
    private val url2 = "https://url2"
    private val urls: List<String> = listOf(url1, url2)

    private val slot = mutableListOf<List<PostBackModel>>()

    private val test1EventId = "idEvent1"
    private val test1SerializedEvent = SerializedEvent(test1EventId, mockk())

    private val test2EventId = "idEvent2"
    private val test2SerializedEvent = SerializedEvent(test2EventId, mockk())

    private val test1LogId = "idLog1"
    private val test1SerializedLog = SerializedLog(test1LogId, "type1", mockk())

    private val propertiesProvider: PostBackModelFactory = mockk {
        every { create(any(), any()) } returns mockk()
    }

    private val cloudRepository: CloudRepository = mockk()

    private val eventsRepository: EventsRepository = mockk()

    private val internalEventsRepository: InternalEventsRepository = mockk()

    private val metricsRepository: MetricsRepository = mockk()

    private val logRepository: LogsRepository = mockk {
        every { getLogs("") } returns emptyList()
    }

    private val logsManager: LogsManager = mockk()

    private val sendServiceProvider: ExecutorServiceProvider = mockk {
        every {
            provideExecutorService()
        } returns MoreExecutors.newDirectExecutorService()
    }

    private val preferencesUseCase: PreferencesUseCase = mockk {
        every { isOfflineModeEnabled() } returns false
    }

    private val useCase: SendDataToServerUseCaseImpl by lazy {
        SendDataToServerUseCaseImpl(
            propertiesProvider,
            cloudRepository,
            eventsRepository,
            internalEventsRepository,
            sendServiceProvider,
            logRepository,
            metricsRepository,
            logsManager,
            preferencesUseCase
        )
    }

    @Before
    fun setup() {
        mockkObject(CloudConfig)
        every { CloudConfig.getUrls() } returns urls
    }

    @After
    fun tearDown() {
        unmockkObject(CloudConfig)
    }

    @Test
    fun `send technical log with empty events and logs`() {
        justRun { cloudRepository.send(capture(slot), url1) }
        justRun { cloudRepository.send(capture(slot), url2) }

        every { eventsRepository.getEvents(url1) } returns emptyList()
        every { eventsRepository.getEvents(url2) } returns emptyList()
        justRun { eventsRepository.deleteEvent(emptyList(), url1) }
        justRun { eventsRepository.deleteEvent(emptyList(), url2) }

        every { logRepository.getLogs(url1) } returns emptyList()
        every { logRepository.getLogs(url2) } returns emptyList()
        justRun { logRepository.deleteLogs(emptyList(), url1) }
        justRun { logRepository.deleteLogs(emptyList(), url2) }
        every { preferencesUseCase.isOfflineModeEnabled() } returns false

        every { metricsRepository.getMetrics(url1) } returns emptyList()
        every { metricsRepository.getMetrics(url2) } returns emptyList()
        justRun { metricsRepository.deleteMetrics(url1) }
        justRun { metricsRepository.deleteMetrics(url2) }

        useCase.send(false)

        Truth.assertThat(slot.size).isEqualTo(2)

        verifyAll {
            cloudRepository.send(capture(slot), url1)
            cloudRepository.send(capture(slot), url2)

            eventsRepository.getEvents(url1)
            eventsRepository.getEvents(url2)
            eventsRepository.deleteEvent(emptyList(), url1)
            eventsRepository.deleteEvent(emptyList(), url2)

            logRepository.getLogs(url1)
            logRepository.getLogs(url2)
            logRepository.deleteLogs(emptyList(), url1)
            logRepository.deleteLogs(emptyList(), url2)

            metricsRepository.getMetrics(url1)
            metricsRepository.getMetrics(url2)
            metricsRepository.deleteMetrics(url1)
            metricsRepository.deleteMetrics(url2)

            propertiesProvider.create(any(), any())
            logsManager wasNot Called
        }
    }

    @Test
    fun `send events and logs`() {
        justRun { cloudRepository.send(capture(slot), url1) }
        justRun { cloudRepository.send(capture(slot), url2) }

        every {
            eventsRepository.getEvents(url1)
        } returns
            listOf(test1SerializedEvent) andThen
            listOf(test2SerializedEvent) andThen
            emptyList()

        every { eventsRepository.getEvents(url2) } returns emptyList()
        justRun { eventsRepository.deleteEvent(any(), any()) }

        every {
            logRepository.getLogs(url1)
        } returns
            listOf(test1SerializedLog) andThen
            emptyList()

        every { logRepository.getLogs(url2) } returns emptyList()
        justRun { logRepository.deleteLogs(any(), any()) }

        every { metricsRepository.getMetrics(url1) } returns emptyList()
        every { metricsRepository.getMetrics(url2) } returns emptyList()
        justRun { metricsRepository.deleteMetrics(url1) }
        justRun { metricsRepository.deleteMetrics(url2) }

        useCase.send(false)

        Truth.assertThat(slot.size).isEqualTo(3)

        verifyAll {
            cloudRepository.send(capture(slot), url1)
            cloudRepository.send(capture(slot), url2)

            eventsRepository.getEvents(url1)
            eventsRepository.getEvents(url2)
            eventsRepository.deleteEvent(any(), any())

            logRepository.getLogs(url1)
            logRepository.getLogs(url2)
            logRepository.deleteLogs(any(), any())

            metricsRepository.getMetrics(url1)
            metricsRepository.getMetrics(url2)
            metricsRepository.deleteMetrics(url1)
            metricsRepository.deleteMetrics(url2)

            propertiesProvider.create(any(), any())
            logsManager wasNot Called
        }
    }
}