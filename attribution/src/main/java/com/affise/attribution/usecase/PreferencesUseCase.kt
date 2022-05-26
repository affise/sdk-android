package com.affise.attribution.usecase

/**
 * Use case to set library preferences
 */
interface PreferencesUseCase {

    /**
     * Sets Offline mode to [enabled] state
     */
    fun setOfflineModeEnabled(enabled: Boolean)

    /**
     * Returns state of Offline mode
     */
    fun isOfflineModeEnabled(): Boolean

    /**
     * Sets Background Tracking to [enabled] state
     */
    fun setBackgroundTrackingEnabled(enabled: Boolean)

    /**
     * Returns state of  Background Tracking
     */
    fun isBackgroundTrackingEnabled(): Boolean

    /**
     * Sets Tracking to [enabled] state
     */
    fun setTrackingEnabled(enabled: Boolean)

    /**
     * Returns state of Tracking
     */
    fun isTrackingEnabled(): Boolean
}
