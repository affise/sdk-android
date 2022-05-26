package com.affise.attribution.metrics

import com.affise.attribution.executors.ExecutorServiceProvider
import com.google.common.truth.Truth
import com.google.common.util.concurrent.MoreExecutors
import io.mockk.*
import org.junit.Test

class MetricsUseCaseTest {

    private val activityName = "activityName"

    private val openTime = 111L

    private val data = "data"

    private val slot = mutableListOf<MetricsData>()

    private val executorServiceProvider: ExecutorServiceProvider = mockk {
        every {
            provideExecutorService()
        } returns MoreExecutors.newDirectExecutorService()
    }

    private val metricsRepository: MetricsRepository = mockk {
        every { addMetricsData(capture(slot), any()) } just Runs
    }

    private val metricsUseCase = MetricsUseCaseImpl(
        executorServiceProvider, metricsRepository
    )

    @Test
    fun addOpenActivityTime() {
        metricsUseCase.addOpenActivityTime(activityName, openTime)

        Truth.assertThat(slot.size).isEqualTo(1)

        val event = slot.first()

        Truth.assertThat(event.activityName).isEqualTo(activityName)
        Truth.assertThat(event.openTime).isEqualTo(openTime)
        Truth.assertThat(event.clicksData).isNull()

        verifyAll {
            executorServiceProvider.provideExecutorService()
            metricsRepository.addMetricsData(capture(slot), any())
        }
    }

    @Test
    fun addClickOnActivity() {
        metricsUseCase.addClickOnActivity(activityName, data)

        Truth.assertThat(slot.size).isEqualTo(1)

        val event = slot.first()

        Truth.assertThat(event.activityName).isEqualTo(activityName)
        Truth.assertThat(event.openTime).isEqualTo(0)
        Truth.assertThat(event.clicksData?.size).isEqualTo(1)

        val clicksData = event.clicksData!!.first()

        Truth.assertThat(clicksData.name).isEqualTo(data)
        Truth.assertThat(clicksData.count).isEqualTo(1)

        verifyAll {
            executorServiceProvider.provideExecutorService()
            metricsRepository.addMetricsData(capture(slot), any())
        }
    }
}