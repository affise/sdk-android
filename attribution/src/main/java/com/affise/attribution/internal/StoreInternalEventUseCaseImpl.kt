package com.affise.attribution.internal

import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.network.CloudConfig

/**
 * UseCase store internal events on device
 *
 * @property executorServiceProvider an Executor that provides methods to manage termination and methods
 * @property repository the sdk events repository provide write, read and delete events.
 */
internal class StoreInternalEventUseCaseImpl(
    private val executorServiceProvider: ExecutorServiceProvider,
    private val repository: InternalEventsRepository
) : StoreInternalEventUseCase {

    /**
     * Store [InternalEvent]
     */
    override fun storeInternalEvent(event: InternalEvent) {
        //Execute in executor service
        executorServiceProvider.provideExecutorService().execute {
            //Save event
            repository.storeEvent(event, CloudConfig.getUrls())
        }
    }
}