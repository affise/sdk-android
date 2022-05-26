package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.LongPropertyProvider
import com.affise.attribution.session.SessionManager

/**
 * Provider for parameter [Parameters.LAST_SESSION_TIME]
 *
 * @property sessionManager to retrieve last interaction time
 */
class LastSessionTimeProvider(
    private val sessionManager: SessionManager
) : LongPropertyProvider() {

    override fun provide(): Long? = sessionManager.getLastInteractionTime()
}