package com.affise.attribution.events

import com.affise.attribution.events.predefined.GDPREvent
import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.network.CloudConfig
import com.affise.attribution.preferences.models.BackgroundTrackingDisabledException
import com.affise.attribution.usecase.PreferencesUseCaseImpl
import com.affise.attribution.preferences.models.TrackingDisabledException
import com.affise.attribution.session.CurrentActiveActivityCountProvider

/**
 * Event use case for store events on device
 *
 * @property executorServiceProvider an Executor that provides methods to manage termination and methods
 * @property repository the events repository provide write, read and delete events.
 * @property eventsManager the manager to work with events
 */
internal class StoreEventUseCaseImpl(
    private val executorServiceProvider: ExecutorServiceProvider,
    private val repository: EventsRepository,
    private val eventsManager: EventsManager,
    private val preferencesUseCase: PreferencesUseCaseImpl,
    private val activityCountProvider: CurrentActiveActivityCountProvider,
    private val logsManager: LogsManager,
    private val isFirstForUserUseCase: IsFirstForUserUseCase
) : StoreEventUseCase {

    /**
     * Store [event] from app
     */
    override fun storeEvent(event: Event) {
        if (isTrackingEnabled() || event !is GDPREvent) {
            executorServiceProvider.provideExecutorService().execute {
                //Update event for isFirstForUser
                isFirstForUserUseCase.updateEvent(event)

                //Save event
                repository.storeEvent(event, CloudConfig.getUrls())

                //Send events
                eventsManager.sendEvents()
            }
        }
    }

    /**
     * Store [event] from webBridge
     */
    override fun storeWebEvent(event: String) {
        if (isTrackingEnabled() || event != GDPREvent.EVENT_NAME) {
            executorServiceProvider.provideExecutorService().execute {
                //Update event for isFirstForUser
                val updatedEvent = isFirstForUserUseCase.updateWebEvent(event)

                //Save event
                repository.storeWebEvent(updatedEvent, CloudConfig.getUrls())

                //Send events
                eventsManager.sendEvents()
            }
        }
    }

    private fun isTrackingEnabled(): Boolean {
        if (!preferencesUseCase.isTrackingEnabled()) {
            logsManager.addSdkError(TrackingDisabledException())
            return false
        }
        if (isAppInBackground()) {
            if (!preferencesUseCase.isBackgroundTrackingEnabled()) {
                logsManager.addSdkError(BackgroundTrackingDisabledException())
                return false
            }
        }

        return true
    }

    private fun isAppInBackground(): Boolean {
        return activityCountProvider.getActivityCount() >= 0
    }
}