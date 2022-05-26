package com.affise.attribution.exceptions

/**
 * Affise exception that is thrown to [Thread.UncaughtExceptionHandler]
 *
 * Helps to indicate that application is crashed by Affise library
 */
class UncaughtAffiseException(
    message: String,
    cause: Throwable
) : IllegalStateException(message, cause)