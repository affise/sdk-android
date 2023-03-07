package com.affise.attribution.internal.predefined

import com.affise.attribution.parameters.Parameters
import com.affise.attribution.internal.InternalEvent
import org.json.JSONObject

/**
 * When session start.
 *
 * @property affiseSessionCount the count of all sessions.
 * @property lifetimeSessionCount the total application work time milliseconds.
 */
internal class SessionStartInternalEvent(
    private val affiseSessionCount: Long,
    private val lifetimeSessionCount: Long,
) : InternalEvent() {

    /**
     * Serialize SessionStartInternalEvent to JSONObject
     *
     * @return JSONObject of SessionStartInternalEvent
     */
    override fun serialize() = JSONObject().apply {
        put(Parameters.AFFISE_SESSION_COUNT, affiseSessionCount)
        put(Parameters.LIFETIME_SESSION_COUNT, lifetimeSessionCount)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = "SessionStart"
}