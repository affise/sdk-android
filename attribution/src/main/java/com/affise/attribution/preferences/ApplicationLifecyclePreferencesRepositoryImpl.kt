package com.affise.attribution.preferences

import com.affise.attribution.preferences.models.ApplicationLifecyclePreferences

/**
 * Implementation of [ApplicationLifecyclePreferencesRepository]
 */
internal class ApplicationLifecyclePreferencesRepositoryImpl :
    ApplicationLifecyclePreferencesRepository {
    override var preferences = ApplicationLifecyclePreferences()
}

