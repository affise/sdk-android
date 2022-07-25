package com.affise.attribution.metrics

import com.affise.attribution.converter.Converter
import com.affise.attribution.events.Event
import com.affise.attribution.events.SerializedEvent
import com.affise.attribution.storages.MetricsStorage
import java.util.Calendar

/**
 * Metrics repository provide write, read and delete metrics event.
 *
 * @property converterToBase64 convert string to encoding Base64 string
 * @property converterToSerializedEvent convert metrics event to SerializedEvent
 * @property metricsStorage storage of metrics events
 */
internal class MetricsRepositoryImpl(
    private val converterToBase64: Converter<String, String>,
    private val converterToSerializedEvent: Converter<Event, SerializedEvent>,
    private val metricsStorage: MetricsStorage
) : MetricsRepository {

    /**
     * Has metrics by [url] or not
     */
    override fun hasMetrics(url: String) = metricsStorage.hasMetrics(
        converterToBase64.convert(url),
        getCurrentDayName()
    )

    /**
     * Get old metrics event from [url]
     * @return list of metrics SerializedEvent
     */
    override fun getMetrics(url: String): List<SerializedEvent> = metricsStorage.getMetricsEvents(
        converterToBase64.convert(url),
        getCurrentDayName()
    ).map {
        //Convert to SerializedEvent
        converterToSerializedEvent.convert(it)
    }

    /**
     * Add [metricsData] to current day for all [urls]
     */
    override fun addMetricsData(metricsData: MetricsData, urls: List<String>) {
        //For all urls
        urls.forEach { url ->
            //Get metrics event by current day
            val actualEvent = metricsStorage.getMetricsEvent(
                converterToBase64.convert(url),
                getCurrentDayName()
            )?.also { currentEvent ->
                currentEvent.data
                    .find {
                        //Find events by activity
                        it.activityName == metricsData.activityName
                    }
                    ?.also { currentData ->
                        //Add open time to activity
                        currentData.openTime += metricsData.openTime

                        //Current data of clicks
                        val currentClicksData = currentData.clicksData ?: mutableListOf()

                        //New data of clicks
                        val newClicksData = metricsData.clicksData ?: mutableListOf()

                        if (currentClicksData.isEmpty()) {
                            currentClicksData.addAll(newClicksData)
                        } else {
                            newClicksData.forEach { clickData ->
                                currentClicksData
                                    .find {
                                        it.name == clickData.name
                                    }
                                    ?.let {
                                        it.count += clickData.count
                                    }
                                    ?: run {
                                        currentClicksData.add(clickData)
                                    }
                            }
                        }
                    }
                    ?: run {
                        currentEvent.data.add(metricsData)
                    }
            } ?: MetricsEvent(getCurrentDay()).also { event ->
                event.data = mutableListOf(metricsData)
            }

            //Save metrics event
            saveMetricsEvent(actualEvent, url)
        }
    }

    /**
     * Delete old metrics by [url]
     */
    override fun deleteMetrics(url: String) {
        metricsStorage.deleteMetrics(
            converterToBase64.convert(url),
            getCurrentDayName()
        )
    }

    /**
     * Removes all metrics events
     */
    override fun clear() {
        metricsStorage.clear()
    }

    /**
     * Save metrics [event] by [url] on current day
     */
    private fun saveMetricsEvent(event: MetricsEvent, url: String) {
        metricsStorage.saveMetricsEvent(
            converterToBase64.convert(url),
            getCurrentDayName(),
            event
        )
    }

    /**
     * Get current day
     * @return current day in millis
     */
    private fun getCurrentDay() = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    /**
     * Get current day dir name
     */
    private fun getCurrentDayName() = getCurrentDay().toString().let {
        converterToBase64.convert(it)
    }
}