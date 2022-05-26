package com.affise.attribution.logs

import org.json.JSONObject

/**
 * Serialized log contains [id] identification, [type] and log [data]
 */
data class SerializedLog(

    /**
     * Log id
     */
    val id: String,

    /**
     * Log type
     */
    val type: String,

    /**
     * Log data
     */
    val data: JSONObject
)