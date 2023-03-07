package com.affise.attribution.usecase

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.affise.attribution.parameters.Parameters
import com.affise.attribution.session.CurrentActiveActivityCountProvider
import com.affise.attribution.utils.generateUUID
import java.util.*

internal class FirstAppOpenUseCase(
    private val preferences: SharedPreferences,
    private val activityCountProvider: CurrentActiveActivityCountProvider
) {

    /**
     * Check preferences for have first opened date and generate properties if no data
     */
    fun onAppCreated() {
        if (preferences.getLong(FIRST_OPENED_DATE_KEY, 0) == 0L) {
            onAppFirstOpen()
        }

        //init session observer
        activityCountProvider.init()
    }

    /**
     * Generate properties on app first open
     */
    @SuppressLint("ApplySharedPref")
    private fun onAppFirstOpen() {
        //Create first open date
        val firstOpenDate = Calendar.getInstance().timeInMillis

        //Create affDevId
        val affDevId = generateUUID().toString()

        //Create affAltDevId
        val affAltDevId = generateUUID().toString()

        //Create randomUserId
        val randomUserId = generateUUID().toString()

        //Save properties
        preferences.edit().apply {
            putLong(FIRST_OPENED_DATE_KEY, firstOpenDate)
            putString(AFF_DEVICE_ID, affDevId)
            putString(AFF_ALT_DEVICE_ID, affAltDevId)
            putString(Parameters.RANDOM_USER_ID, randomUserId)
            putBoolean(FIRST_OPENED, true)
        }.commit()
    }

    /**
     * Get first open
     * @return is first open
     */
    fun isFirstOpen() = preferences
        .getBoolean(FIRST_OPENED, true)
        .let {
            if (it) {
                //Save properties
                preferences.edit().apply {
                    putBoolean(FIRST_OPENED, false)
                }.apply()
                true
            } else {
               false
            }
        }

    /**
     * Get first open date
     * @return first open date
     */
    fun getFirstOpenDate() = preferences
        .getLong(FIRST_OPENED_DATE_KEY, 0)
        .let { if (it == 0L) null else Date(it) }

    /**
     * Get devise id
     * @return devise id
     */
    fun getAffiseDeviseId() = preferences.getString(AFF_DEVICE_ID, "")

    /**
     * Get alt devise id
     * @return alt devise id
     */
    fun getAffiseAltDeviseId() = preferences.getString(AFF_ALT_DEVICE_ID, "")

    /**
     * Get random user id
     * @return random user id
     */
    fun getRandomUserId() = preferences.getString(Parameters.RANDOM_USER_ID, "")

    companion object {
        private const val FIRST_OPENED = "FIRST_OPENED"
        private const val FIRST_OPENED_DATE_KEY = "FIRST_OPENED_DATE_KEY"
        private const val AFF_DEVICE_ID = "AFF_DEVICE_ID"
        private const val AFF_ALT_DEVICE_ID = "AFF_ALT_DEVICE_ID"
    }
}