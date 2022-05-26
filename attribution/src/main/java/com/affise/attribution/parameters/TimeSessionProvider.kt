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

    override fun provide(): Long = sessionManager.getSessionTime()
}