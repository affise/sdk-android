package com.affise.attribution.executors

import java.util.concurrent.ExecutorService

/**
 * Executor service provider interface
 */
interface ExecutorServiceProvider {

    /**
     * Provide executor service
     *
     * @return executor
     */
    fun provideExecutorService(): ExecutorService
}