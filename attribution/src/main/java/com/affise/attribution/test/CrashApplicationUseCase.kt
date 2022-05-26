package com.affise.attribution.test

import com.affise.attribution.exceptions.TestApplicationCrashException

/**
 * Use case to help app testing
 */
interface CrashApplicationUseCase {

    /**
     * Throws [TestApplicationCrashException]
     */
    fun crash()
}


