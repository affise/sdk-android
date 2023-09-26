package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.LongPropertyProvider
import com.affise.attribution.session.SessionManager

/**
 * Provider for parameter [ProviderType.LIFETIME_SESSION_COUNT]
 *
 * @property sessionManager to retrieve lifetime session time
 */
class LifetimeSessionCountProvider(
    private val sessionManager: SessionManager
) : LongPropertyProvider() {

    override val order: Float = 57.0f
    override val key: ProviderType = ProviderType.LIFETIME_SESSION_COUNT

    override fun provide(): Long = sessionManager.getLifetimeSessionTime()
}