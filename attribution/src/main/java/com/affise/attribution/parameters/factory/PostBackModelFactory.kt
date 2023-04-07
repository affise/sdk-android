package com.affise.attribution.parameters.factory

import com.affise.attribution.parameters.base.PropertyProvider
import com.affise.attribution.parameters.base.Provider
import com.affise.attribution.parameters.base.StringWithParamPropertyProvider
import com.affise.attribution.events.SerializedEvent
import com.affise.attribution.logs.SerializedLog
import com.affise.attribution.network.entity.PostBackModel
import com.affise.attribution.parameters.AffAppTokenPropertyProvider
import com.affise.attribution.parameters.CreatedTimeProvider

internal class PostBackModelFactory(
    providers: List<Provider>
) {
    private val allProviders: MutableList<Provider> = providers.toMutableList()

    private fun mapProviders(): Map<String, Any?> {
        val createdTime = getProvider<CreatedTimeProvider>()?.provideWithDefault()
        val sorted = allProviders.sortedBy { it.order }.filter { it.key != null }
        return  sorted.mapNotNull { provider ->
            provider.key?.let {
                it to when (provider) {
                    is CreatedTimeProvider -> createdTime
                    is PropertyProvider<*> -> provider.provideWithDefault()
                    is StringWithParamPropertyProvider -> {
                        when(provider) {
                            is AffAppTokenPropertyProvider -> provider.provideWithParamAndDefault(createdTime?.toString() ?: "")
                            else -> null
                        }
                    }
                    else -> null
                }
            }
        }.toMap()
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
        return allProviders.firstOrNull { it is T } as? T
    }

    fun addProviders(list: List<Provider>) {
        allProviders.addAll(list)
    }
}