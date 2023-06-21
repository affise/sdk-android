package com.affise.attribution.parameters.factory

import com.affise.attribution.events.SerializedEvent
import com.affise.attribution.logs.SerializedLog
import com.affise.attribution.network.entity.PostBackModel
import com.affise.attribution.parameters.base.*

internal class PostBackModelFactory(
    providers: List<Provider>
) {
    private val allProviders: MutableList<Provider> = providers.toMutableList()

    private fun mapProviders(): Map<String, Any?> {
        return allProviders.mapProviders()
    }

    /**
     * Create PostBackModel with [events] and [logs]
     *
     * @return PostBackModel
     */
    fun create(
        events: List<SerializedEvent> = emptyList(),
        logs: List<SerializedLog> = emptyList(),
        metrics: List<SerializedEvent> = emptyList(),
        internalEvents: List<SerializedEvent> = emptyList(),
    ): PostBackModel {
        return PostBackModel(
            parameters = mapProviders(),
            events = events,
            logs = logs,
            metrics = metrics,
            internalEvents = internalEvents,
        )
    }

    inline fun <reified T: Provider> getProvider(): T? {
        return allProviders.getProvider()
    }

    fun addProviders(list: List<Provider>) {
        allProviders.addAll(list)
    }

    fun getProviders(): List<Provider> = allProviders
}