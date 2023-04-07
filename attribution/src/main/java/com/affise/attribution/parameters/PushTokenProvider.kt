package com.affise.attribution.parameters

import android.content.SharedPreferences
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.PUSHTOKEN]
 *
 * @property preferences to retrieve push token
 */
class PushTokenProvider(
    private val preferences: SharedPreferences
) : StringPropertyProvider() {

    override val order: Float = 65.0f
    override val key: String = Parameters.PUSHTOKEN

    override fun provide(): String? = preferences.getString(KEY_APP_PUSHTOKEN, null)

    companion object {
        const val KEY_APP_PUSHTOKEN = "com.affise.attribution.init.PUSHTOKEN"
    }
}