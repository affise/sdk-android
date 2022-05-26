package com.affise.attribution.metrics

internal interface MetricsUseCase {
    fun addOpenActivityTime(activityName: String, openTime: Long)
    fun addClickOnActivity(activityName: String, data: String)
}