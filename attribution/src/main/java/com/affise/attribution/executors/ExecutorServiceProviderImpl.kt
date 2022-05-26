package com.affise.attribution.executors

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Executor service provider
 *
 * @property threadName the name of use thread.
 */
class ExecutorServiceProviderImpl(
    private val threadName: String
) : ExecutorServiceProvider {

    /**
     * Executor
     */
    private val executor = Executors.newSingleThreadExecutor {
        Thread(it, threadName)
    }

    /**
     * Provide executor service
     */
    override fun provideExecutorService(): ExecutorService = executor
}