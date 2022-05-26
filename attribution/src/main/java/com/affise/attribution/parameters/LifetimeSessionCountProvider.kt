package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.LongPropertyProvider
import com.affise.attribution.session.SessionManager

/**
 * Provider for parameter [Parameters.LIFETIME_SESSION_COUNT]
 *
 * @property sessionManager to retrieve lifetime session time
 */
class LifetimeSessionCountProvider(
    private val sessionManager: SessionManager
) : LongPropertyProvider() {

    override fun provide(): Long = sessionManager.getLifetimeSessionTime()
}