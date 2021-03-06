package com.affise.attribution.parameters

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.affise.attribution.parameters.base.StringPropertyProvider
import java.util.UUID

/**
 * Provider for parameter [Parameters.REFTOKEN]
 *
 * @property preferences to retrieve reftoken
 */
class RefTokenProvider(
    private val preferences: SharedPreferences
) : StringPropertyProvider() {

    @SuppressLint("ApplySharedPref")
    override fun provide(): String {
        //Get token
        val token = preferences.getString(KEY, null)

        //Check token
        return if (token == null) {
            //If token is empty generate new token
            val newToken = UUID.randomUUID().toString()

            //Save token to preferences
            preferences.edit().apply {
                putString(KEY, newToken)
            }.commit()

            newToken
        } else {
            token
        }
    }

    companion object {
        private const val KEY = "com.affise.attribution.parameters.REFTOKEN"
    }
}