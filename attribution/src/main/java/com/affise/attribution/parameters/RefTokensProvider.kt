package com.affise.attribution.parameters

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.utils.generateUUID

/**
 * Provider for parameter [Parameters.REFTOKENS]
 *
 * @property preferences to retrieve reftoken
 */
class RefTokensProvider(
    private val preferences: SharedPreferences
) : StringPropertyProvider() {

    override val order: Float = 33.0f
    override val key: String = Parameters.REFTOKENS

    @SuppressLint("ApplySharedPref")
    override fun provide(): String {
        //Get token
        val token = preferences.getString(KEY, null)

        //Check token
        return if (token == null) {
            //If token is empty generate new token
            val newToken = generateUUID().toString()

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
        private const val KEY = "com.affise.attribution.parameters.REFTOKENS"
    }
}