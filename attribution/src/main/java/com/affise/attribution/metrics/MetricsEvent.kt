package com.affise.attribution.metrics

import com.affise.attribution.events.Event
import org.json.JSONArray
import org.json.JSONObject

internal class MetricsEvent(val date: Long) : Event() {

    /**
     * Event data
     */
    var data: MutableList<MetricsData> = mutableListOf()

    /**
     * Serialize event to JSONObject
     */
    override fun serialize(): JSONObject = JSONObject().apply {
        put(KEY_DATE, date)

        val data = JSONArray().apply {
            data.forEach {
                val item = JSONObject().apply {
                    put(KEY_ACTIVITY_NAME, it.activityName)
                    put(KEY_OPEN_TIME, it.openTime)

                    val clicksData = JSONArray().apply {
                        it.clicksData?.forEach {
                            val d = JSONObject().apply {
                                put(KEY_NAME, it.name)
                                put(KEY_COUNT, it.count)
                            }

                            put(d)
                        }
                    }

                    put(KEY_CLICKS_DATA, clicksData)
                }

                put(item)
            }
        }

        put(KEY_DATA, data)
    }

    override fun getName(): String = "MetricsEvent"

    override fun getUserData(): String = "Auto generate metrics"

    override fun getCategory(): String = "autoNative"

    companion object {
        const val KEY_DATE = "begin_day_timestamp"
        const val KEY_DATA = "data"

        const val KEY_ACTIVITY_NAME = "activity_mame"
        const val KEY_OPEN_TIME = "open_time"
        const val KEY_CLICKS_DATA = "clicks_data"

        const val KEY_NAME = "name"
        const val KEY_COUNT = "count"
    }
}