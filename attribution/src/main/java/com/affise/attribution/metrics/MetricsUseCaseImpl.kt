package com.affise.attribution.metrics

import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.network.CloudConfig

/**
 * Metrics use case for store metrics events on device
 *
 * @property executorServiceProvider an Executor that provides methods to manage termination and methods
 * @property metricsRepository the metrics events repository provide write, read and delete events.
 */
internal class MetricsUseCaseImpl(
    private val executorServiceProvider: ExecutorServiceProvider,
    private val metricsRepository: MetricsRepository
) : MetricsUseCase {

    /**
     * Add [openTime] to current activity with name [activityName]
     */
    override fun addOpenActivityTime(activityName: String, openTime: Long) {
        executorServiceProvider.provideExecutorService().execute {
            //Create MetricsData by activityName and openTime
            val metricsData = MetricsData().also {
                it.activityName = activityName
                it.openTime = openTime
            }

            //Add MetricsData to repository
            metricsRepository.addMetricsData(metricsData, CloudConfig.getUrls())
        }
    }

    /**
     * Add click [data] with [activityName]
     */
    override fun addClickOnActivity(activityName: String, data: String) {
        executorServiceProvider.provideExecutorService().execute {
            //Create MetricsClickData by ClickData
            val clickData = MetricsClickData().also {
                it.name = data
                it.count = 1
            }

            //Create MetricsData by activityName and ClickData
            val metricsData = MetricsData().also {
                it.activityName = activityName
                it.clicksData = mutableListOf(clickData)
            }

            //Add MetricsData to repository
            metricsRepository.addMetricsData(metricsData, CloudConfig.getUrls())
        }
    }
}