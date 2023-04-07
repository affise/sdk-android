package com.affise.attribution.network.entity

import com.affise.attribution.events.SerializedEvent
import com.affise.attribution.logs.SerializedLog

data class PostBackModel(
    val parameters: Map<String, Any?> = emptyMap(),
    val events: List<SerializedEvent>? = null,
    val logs: List<SerializedLog>? = null,
    val metrics: List<SerializedEvent>? = null,
    val internalEvents: List<SerializedEvent>? = null,
)
