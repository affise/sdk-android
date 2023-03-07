package com.affise.attribution.internal


/**
 * UseCase store InternalEvent interface
 */
internal interface StoreInternalEventUseCase {

    /**
     * Store InternalEvent
     */
    fun storeInternalEvent(event: InternalEvent)
}