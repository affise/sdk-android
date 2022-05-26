package com.affise.attribution.preferences

import android.content.SharedPreferences
import com.affise.attribution.preferences.models.ApplicationLifetimePreferences

/**
 * Implementation of [ApplicationLifetimePreferencesRepository]
 *
 * @property sharedPreferences to store [ApplicationLifetimePreferences] model
 */
internal class ApplicationLifetimePreferencesRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : ApplicationLifetimePreferencesRepository {
    override var preferences: ApplicationLifetimePreferences
        get() {
            return ApplicationLifetimePreferences(
                trackingEnabled = sharedPreferences.getBoolean(PREFERENCE_TRACKING, true)
            )
        }
        set(value) {
            sharedPreferences.edit()
                .apply {
                    putBoolean(PREFERENCE_TRACKING, value.trackingEnabled)
                }
                .apply()
        }

    companion object {
        private const val PREFERENCE_TRACKING =
            "com.affise.attribution.preferences.PREFERENCE_TRACKING"
    }
}