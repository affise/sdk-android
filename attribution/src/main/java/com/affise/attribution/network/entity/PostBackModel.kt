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
