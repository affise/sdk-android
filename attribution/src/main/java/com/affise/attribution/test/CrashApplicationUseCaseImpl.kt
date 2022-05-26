package com.affise.attribution.test

import com.affise.attribution.exceptions.TestApplicationCrashException

/**
 * Implementation of [CrashApplicationUseCase]
 */
internal class CrashApplicationUseCaseImpl : CrashApplicationUseCase {
    override fun crash() {
        throw TestApplicationCrashException()
    }
}