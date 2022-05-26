package com.affise.attribution.usecase

import com.affise.attribution.events.EventsRepository
import com.affise.attribution.events.GDPREventRepository

/**
 * Implementation of [EraseUserDataUseCase]
 *
 * @property eventsRepository to access user events
 * @property gdprRepository to access user GDPR event
 */
internal class EraseUserDataUseCaseImpl(
    private val eventsRepository: EventsRepository,
    private val gdprRepository: GDPREventRepository
): EraseUserDataUseCase {
    override fun eraseUserData() {
        eventsRepository.clear()
        gdprRepository.clear()
    }
}