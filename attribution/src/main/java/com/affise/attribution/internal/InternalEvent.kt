package com.affise.attribution.internal

import com.affise.attribution.utils.timestamp
import org.json.JSONObject

/**
 * Base internal event
 */
abstract class InternalEvent {

    /**
     * Name of event
     *
     * @return name
     */
    abstract fun getName(): String

    /**
     * Event timestamp
     */
    fun getTimestamp(): Long = timestamp()

    /**
     * Serialize event to JSONObject
     *
     * @return JSONObject
     */
    abstract fun serialize(): JSONObject
}