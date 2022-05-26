package com.affise.attribution.preferences.models

/**
 * Model describes preferences which persist until application restart
 *
 * @property offlineMode when enabled, no network activity should be triggered by library
 * @property backgroundTracking when disabled, library should not generate any tracking events while in background
 */
internal data class ApplicationLifecyclePreferences(
    val offlineMode: Boolean = false,
    val backgroundTracking: Boolean = true
)