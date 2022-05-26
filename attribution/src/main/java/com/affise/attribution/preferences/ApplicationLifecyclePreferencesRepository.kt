package com.affise.attribution.preferences

import com.affise.attribution.preferences.models.ApplicationLifecyclePreferences

/**
 * Repository to store [ApplicationLifecyclePreferences]
 */
internal interface ApplicationLifecyclePreferencesRepository {

    /**
     * property to access to model stored by repository
     */
    var preferences: ApplicationLifecyclePreferences
}
