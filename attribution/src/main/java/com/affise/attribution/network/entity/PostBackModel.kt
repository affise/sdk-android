package com.affise.attribution.network.entity

import com.affise.attribution.events.SerializedEvent
import com.affise.attribution.logs.SerializedLog
import com.affise.attribution.parameters.ProviderType

data class PostBackModel(
    val parameters: Map<ProviderType, Any?> = emptyMap(),
    val events: List<SerializedEvent>? = null,
    val logs: List<SerializedLog>? = null,
    val metrics: List<SerializedEvent>? = null,
    val internalEvents: List<SerializedEvent>? = null,
)

internal fun PostBackModel.asFirstOpen(): PostBackModel = PostBackModel(
    parameters = this.parameters.entries.associate {
        return@associate if (it.key == ProviderType.INSTALL_FIRST_EVENT) {
            it.key to true
        } else {
            it.key to it.value
        }
    },
    events = this.events,
    logs = this.logs,
    metrics = this.metrics,
    internalEvents = this.internalEvents,
)
