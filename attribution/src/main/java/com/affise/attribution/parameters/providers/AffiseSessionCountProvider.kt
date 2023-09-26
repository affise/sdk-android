package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.LongPropertyProvider
import com.affise.attribution.session.SessionManager

/**
 * Provider for parameter [ProviderType.AFFISE_SESSION_COUNT]
 *
 * @property sessionManager to retrieve session count
 */
internal class AffiseSessionCountProvider(
    private val sessionManager: SessionManager
) : LongPropertyProvider() {

    override val order: Float = 56.0f
    override val key: ProviderType = ProviderType.AFFISE_SESSION_COUNT

    override fun provide(): Long = sessionManager.getSessionCount()
        .let { if (it == 0L) 1L else it }
}