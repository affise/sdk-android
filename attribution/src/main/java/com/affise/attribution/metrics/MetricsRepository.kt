package com.affise.attribution.metrics

import com.affise.attribution.events.SerializedEvent

internal interface MetricsRepository {
    fun getMetrics(url: String): List<SerializedEvent>
    fun addMetricsData(metricsData: MetricsData, urls: List<String>)
    fun deleteMetrics(url: String)
    fun clear()
}