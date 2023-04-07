package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.LongPropertyProvider
import com.affise.attribution.session.SessionManager

/**
 * Provider for parameter [Parameters.TIME_SESSION]
 *
 * @property sessionManager to retrieve session time
 */
class TimeSessionProvider(
    private val sessionManager: SessionManager
) : LongPropertyProvider() {

    override val order: Float = 55.0f
    override val key: String = Parameters.TIME_SESSION

    override fun provide(): Long = sessionManager.getSessionTime()
}