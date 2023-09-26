package com.affise.attribution.internal.predefined

import com.affise.attribution.internal.InternalEvent
import com.affise.attribution.internal.InternalEventName
import com.affise.attribution.parameters.ProviderType

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

    init {
        addPropertyRaw(ProviderType.AFFISE_SESSION_COUNT.provider, affiseSessionCount)
        addPropertyRaw(ProviderType.LIFETIME_SESSION_COUNT.provider, lifetimeSessionCount)
    }

    /**
     * Name of event
     *
     * @return name
     */
    override fun getName() = InternalEventName.SESSION_START
}