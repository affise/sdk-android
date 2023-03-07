package com.affise.attribution.internal

import org.json.JSONObject
import java.util.*

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
    fun getTimestamp(): Long = Calendar.getInstance().timeInMillis

    /**
     * Serialize event to JSONObject
     *
     * @return JSONObject
     */
    abstract fun serialize(): JSONObject
}