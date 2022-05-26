package com.affise.attribution.session

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.affise.attribution.parameters.Parameters
import java.util.Calendar

class SessionManagerImpl(
    private val preferences: SharedPreferences,
    private val activityCountProvider: CurrentActiveActivityCountProvider
) : SessionManager {

    /**
     * Time of start session
     */
    private var openAppTime: Long? = null

    /**
     * Last time of user active
     */
    private var closeAppTime: Long? = null

    /**
     * Session active status
     */
    private var sessionActive: Boolean = false

    /**
     * Open app status
     */
    private var isOpenApp: Boolean = false

    /**
     * Start manager
     */
    override fun init() = subscribeToActivityEvents()

    /**
     * Subscribe to change open activity count
     */
    private fun subscribeToActivityEvents() {
        activityCountProvider.addActivityCountListener { count ->
            //Check open activity count
            if (count > 0) {
                //App is open
                isOpenApp = true

                /**
                 * Check create open app time
                 */
                if (openAppTime == null) {
                    //open app time
                    openAppTime = Calendar.getInstance().timeInMillis
                }
            } else {
                //Update session status if need
                checkSessionToStart()

                //Save time of user quit or hide app
                closeAppTime = Calendar.getInstance().timeInMillis

                //App is close
                isOpenApp = false

                //Drop session status
                sessionActive = false

                //Save sessionTime
                saveSessionTime()

                //Drop open app time
                openAppTime = null
            }
        }
    }

    /**
     * Getting the last time the user was in the application
     * @return time
     */
    override fun getLastInteractionTime() = when {
        //Current time if app is open
        isOpenApp -> Calendar.getInstance().timeInMillis
        //lastInteractionTime is session is active
        else -> closeAppTime
    }

    /**
     * Get session active status
     *
     * @return session status
     */
    override fun isSessionActive(): Boolean {
        //Check session status
        checkSessionToStart()

        //Return session status
        return sessionActive
    }

    /**
     * Check time of start app and start session
     */
    private fun checkSessionToStart() {
        if (sessionActive) return

        //Check open app time
        openAppTime?.let { startTime ->
            //Time current session
            val time = Calendar.getInstance().timeInMillis - startTime - TIME_TO_START_SESSION

            //if session started
            if (time > 0) {
                sessionActive = true

                //Save new session
                addNewSession()
            }
        }
    }

    /**
     * Save session time
     */
    @SuppressLint("ApplySharedPref")
    private fun saveSessionTime() {
        preferences
            .edit()
            .putLong(Parameters.LIFETIME_SESSION_COUNT, getLifetimeSessionTime())
            .commit()
    }

    /**
     * Get all old sessions time
     */
    private fun getSaveSessionsTime() = preferences.getLong(Parameters.LIFETIME_SESSION_COUNT, 0L)

    /**
     * Save new session count
     */
    @SuppressLint("ApplySharedPref")
    private fun addNewSession() {
        val count = preferences.getLong(Parameters.AFFISE_SESSION_COUNT, 0L)

        preferences
            .edit()
            .putLong(Parameters.AFFISE_SESSION_COUNT, count + 1)
            .commit()
    }

    /**
     * Get session cont
     */
    override fun getSessionCount(): Long {
        //Check session status
        if (!sessionActive) {
            checkSessionToStart()
        }

        return preferences.getLong(Parameters.AFFISE_SESSION_COUNT, 0L)
    }

    /**
     * Get current session time
     */
    override fun getSessionTime() = openAppTime?.let { startTime ->
        Calendar.getInstance().timeInMillis - startTime
    } ?: 0

    /**
     * Get all sessions time
     */
    override fun getLifetimeSessionTime() = getSaveSessionsTime() + getSessionTime()

    companion object {
        private const val TIME_TO_START_SESSION = 15 * 1000L
    }
}