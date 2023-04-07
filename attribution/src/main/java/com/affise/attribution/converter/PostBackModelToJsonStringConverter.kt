package com.affise.attribution.converter

import com.affise.attribution.network.entity.PostBackModel
import com.affise.attribution.parameters.Parameters
import org.json.JSONArray
import org.json.JSONObject

/**
 * Converter List<PostBackModel> to String
 */
class PostBackModelToJsonStringConverter : Converter<List<PostBackModel>, String> {

    /**
     * Convert [from] list of PostBackModel to json string
     */
    override fun convert(from: List<PostBackModel>): String {
        //Create jsonArray
        val jsonArray = JSONArray()

        //Create jsonObject for all objects in list
        from.forEach { model ->
            val jsonObject = parameters(model)
            jsonArray.put(jsonObject)
        }

        //Create json string
        return jsonArray
            .toString()
            .replace("\\/", "/")
    }

    /**
     * Create parameters map of object
     */
    private fun parameters(obj: PostBackModel) = JSONObject().apply {
        //Parameters
        obj.parameters.forEach {
            put(it.key, it.value)
        }

        //Events
        val eventsArray = JSONArray()
        obj.events?.forEach { event ->
            eventsArray.put(event.data)
        }
        put(Parameters.AFFISE_EVENTS_COUNT, eventsArray.length())
        put(EVENTS_KEY, eventsArray)

        //SdkEvents
        val sdkEventsArray = JSONArray()
        obj.internalEvents?.forEach { event ->
            sdkEventsArray.put(event.data)
        }
        put(Parameters.AFFISE_INTERNAL_EVENTS_COUNT, sdkEventsArray.length())
        put(INTERNAL_EVENTS_KEY, sdkEventsArray)

        //Logs
        val logsArray = JSONArray()
        obj.logs?.forEach { log ->
            logsArray.put(log.data)
        }
        put(Parameters.AFFISE_SDK_EVENTS_COUNT, logsArray.length())
        put(SDK_EVENTS_KEY, logsArray)

        //Metrics
        val metricsArray = JSONArray()
        obj.metrics?.forEach { metric ->
            metricsArray.put(metric.data)
        }
        put(Parameters.AFFISE_METRICS_EVENTS_COUNT, metricsArray.length())
        put(METRICS_EVENTS_KEY, metricsArray)
    }

    companion object {
        private const val EVENTS_KEY = "events"
        private const val SDK_EVENTS_KEY = "sdk_events"
        private const val METRICS_EVENTS_KEY = "metrics_events"
        private const val INTERNAL_EVENTS_KEY = "internal_events"
    }
}