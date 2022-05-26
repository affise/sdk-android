package com.affise.attribution.preferences.models

/**
 * Model describes preferences which persist until application reinstall
 *
 * @property trackingEnabled when disabled, library should not generate any tracking events
 */
internal data class ApplicationLifetimePreferences(
    val trackingEnabled: Boolean = true
)