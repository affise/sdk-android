package com.affise.attribution.logs

import com.affise.attribution.events.predefined.AffiseLog
import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.network.CloudConfig

/**
 * Logs use case for store logs on device
 *
 * @property executorServiceProvider an Executor that provides methods to manage termination and methods
 * @property repository the logs repository provide write, read and delete logs.
 */
internal class StoreLogsUseCaseImpl(
    private val executorServiceProvider: ExecutorServiceProvider,
    private val repository: LogsRepository
) : StoreLogsUseCase {

    /**
     * Store [log]
     */
    override fun storeLog(log: AffiseLog) {
        //Execute in executor service
        executorServiceProvider.provideExecutorService().execute {
            //Store log
            repository.storeLog(log, CloudConfig.getUrls())
        }
    }
}