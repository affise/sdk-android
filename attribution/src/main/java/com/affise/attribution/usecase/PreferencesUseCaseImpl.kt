package com.affise.attribution.usecase

import com.affise.attribution.preferences.ApplicationLifecyclePreferencesRepositoryImpl
import com.affise.attribution.preferences.ApplicationLifetimePreferencesRepositoryImpl

/**
 * Implementation of [PreferencesUseCase]
 *
 * @property repository to store preferences that is persisted until app restart
 * @property lifetimeRepository to store preferences that is persisted until app reinstall
 */
internal class PreferencesUseCaseImpl(
    private val repository: ApplicationLifecyclePreferencesRepositoryImpl,
    private val lifetimeRepository: ApplicationLifetimePreferencesRepositoryImpl
) : PreferencesUseCase {

    override fun setOfflineModeEnabled(enabled: Boolean) {
        repository.preferences = repository.preferences.copy(offlineMode = enabled)
    }

    override fun isOfflineModeEnabled(): Boolean = repository.preferences.offlineMode

    override fun setBackgroundTrackingEnabled(enabled: Boolean) {
        repository.preferences = repository.preferences.copy(backgroundTracking = enabled)
    }

    override fun isBackgroundTrackingEnabled() = repository.preferences.backgroundTracking

    override fun setTrackingEnabled(enabled: Boolean) {
        lifetimeRepository.preferences =
            lifetimeRepository.preferences.copy(trackingEnabled = enabled)
    }

    override fun isTrackingEnabled(): Boolean = lifetimeRepository.preferences.trackingEnabled
}