package com.affise.attribution.converter

import com.affise.attribution.metrics.MetricsClickData
import com.affise.attribution.metrics.MetricsData
import com.affise.attribution.metrics.MetricsEvent
import org.json.JSONArray
import org.json.JSONObject

/**
 * Converter from JSONObject to MetricsEvent
 */
internal class JsonObjectToMetricsEventConverter : Converter<JSONObject, MetricsEvent> {

    /**
     * Convert [from] JSONObject to MetricsEvent
     */
    override fun convert(from: JSONObject): MetricsEvent = MetricsEvent(
        from.optLong(MetricsEvent.KEY_DATE)
    ).also {
        val data = from.optJSONArray(MetricsEvent.KEY_DATA) ?: JSONArray()

        it.data = (0 until data.length())
            .map { positionMetricsData ->
                MetricsData().apply {
                    val saveMetricsData = data.optJSONObject(positionMetricsData)

                    activityName = saveMetricsData.optString(MetricsEvent.KEY_ACTIVITY_NAME)

                    openTime = saveMetricsData.optLong(MetricsEvent.KEY_OPEN_TIME)

                    clicksData = saveMetricsData.optJSONArray(MetricsEvent.KEY_CLICKS_DATA)
                        ?.let { clicksData ->
                            (0 until clicksData.length()).map { positionClicksData ->
                                val saveClicksData =
                                    clicksData.optJSONObject(positionClicksData)

                                MetricsClickData().apply {
                                    name = saveClicksData.optString(MetricsEvent.KEY_NAME)

                                    count = saveClicksData.optInt(MetricsEvent.KEY_COUNT)
                                }
                            }
                        }?.toMutableList()
                }
            }.toMutableList()
    }
}