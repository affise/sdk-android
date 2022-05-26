package com.affise.attribution.metrics

/**
 * Metrics data by activity
 */
internal class MetricsData {
    /**
     * Name of activity
     */
    var activityName: String? = null

    /**
     * All open time current activity
     */
    var openTime: Long = 0

    /**
     * Data of clicks on activity
     */
    var clicksData: MutableList<MetricsClickData>? = null
}

/**
 * Metrics click data by views
 */
internal class MetricsClickData {
    /**
     * Name dat of click
     */
    var name: String? = null

    /**
     * Count of click current data
     */
    var count: Int = 0
}