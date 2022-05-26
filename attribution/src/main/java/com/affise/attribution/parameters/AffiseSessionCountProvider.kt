package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.LongPropertyProvider
import com.affise.attribution.session.SessionManager

/**
 * Provider for parameter [Parameters.AFFISE_SESSION_COUNT]
 *
 * @property sessionManager to retrieve session count
 */
internal class AffiseSessionCountProvider(
    private val sessionManager: SessionManager
) : LongPropertyProvider() {

    override fun provide(): Long = sessionManager.getSessionCount()
        .let { if (it == 0L) 1L else it }
}