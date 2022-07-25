package com.affise.attribution.storages

import com.affise.attribution.metrics.MetricsEvent

internal interface MetricsStorage {
    fun hasMetrics(key: String, ignoreSubKey: String): Boolean
    fun getMetricsEvents(key: String, ignoreSubKey: String): List<MetricsEvent>
    fun getMetricsEvent(key: String, subKey: String): MetricsEvent?
    fun saveMetricsEvent(key: String, subKey: String, event: MetricsEvent)
    fun deleteMetrics(key: String, ignoreSubKey: String)
    fun clear()
}