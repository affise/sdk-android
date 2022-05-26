package com.affise.attribution.logs

import com.affise.attribution.exceptions.UncaughtAffiseException

/**
 * Implementation of [Thread.UncaughtExceptionHandler]
 */
class AffiseThreadUncaughtExceptionHandlerImpl(
    private val delegate: Thread.UncaughtExceptionHandler?,
    private val logsManager: LogsManager
) : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread, e: Throwable) {
        val stackTrace = e.stackTraceToString()
        if (stackTrace.contains("com.affise")) {
            UncaughtAffiseException("Affise library uncaught exception on $t", e)
                .also(logsManager::addSdkError)
        }

        delegate?.uncaughtException(t, e)
    }
}