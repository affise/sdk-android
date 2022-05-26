package com.affise.attribution.preferences

import com.affise.attribution.preferences.models.ApplicationLifetimePreferences

/**
 * Repository for [ApplicationLifetimePreferences] model
 */
internal interface ApplicationLifetimePreferencesRepository {

    /**
     * property to access to model stored by repository
     */
    var preferences: ApplicationLifetimePreferences
}