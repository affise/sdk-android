package com.affise.attribution.events

/**
 * Event use case for IsFirstForUser
 */
interface IsFirstForUserUseCase {

    /**
     * Update IsFirstForUser
     */
    fun updateEvent(event: Event)

    /**
     * Update IsFirstForUser for webBridge
     */
    fun updateWebEvent(event: String): String
}