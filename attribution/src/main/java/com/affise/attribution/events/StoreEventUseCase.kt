package com.affise.attribution.events

/**
 * Event use case for store events
 */
interface StoreEventUseCase {

    /**
     * Store event
     */
    fun storeEvent(event: Event)

    /**
     * Store web event
     */
    fun storeWebEvent(event: String)
}