package com.affise.attribution.usecase

import android.content.SharedPreferences
import com.affise.attribution.parameters.Parameters
import com.affise.attribution.session.CurrentActiveActivityCountProvider
import com.affise.attribution.utils.*
import com.affise.attribution.utils.generateUUID
import com.affise.attribution.utils.timestamp
import com.affise.attribution.utils.saveBoolean
import com.affise.attribution.utils.saveLong
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
        checkSaveUUIDs()

        //init session observer
        activityCountProvider.init()
    }

    /**
     * Generate properties on app first open
     */
    private fun onAppFirstOpen() {
        //Create first open date
        val firstOpenDate = timestamp()

        checkSaveUUIDs()

        //Save properties
        preferences.saveBoolean(FIRST_OPENED, true)
        preferences.saveLong(FIRST_OPENED_DATE_KEY, firstOpenDate)
    }

    private fun checkSaveUUIDs() {
        preferences.apply {
            //Create affDevId
            checkSaveString(AFF_DEVICE_ID) {
                generateUUID().toString()
            }
            //Create affAltDevId
            checkSaveString(AFF_ALT_DEVICE_ID) {
                generateUUID().toString()
            }
            //Create randomUserId
            checkSaveString(Parameters.RANDOM_USER_ID) {
                generateUUID().toString()
            }
        }
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