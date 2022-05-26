package com.affise.attribution.events

import org.json.JSONObject

/**
 * Serialized event to store
 */
data class SerializedEvent(
    /**
     * Event id
     */
    val id: String,

    /**
     * Event data
     */
    val data: JSONObject
)