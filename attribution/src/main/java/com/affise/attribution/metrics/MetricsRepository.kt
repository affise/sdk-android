package com.affise.attribution.metrics

import com.affise.attribution.events.SerializedEvent

internal interface MetricsRepository {
    fun hasMetrics(url: String): Boolean
    fun getMetrics(url: String): List<SerializedEvent>
    fun addMetricsData(metricsData: MetricsData, urls: List<String>)
    fun deleteMetrics(url: String)
    fun clear()
}