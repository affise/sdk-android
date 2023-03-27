package com.affise.attribution.session

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.SystemClock
import com.affise.attribution.parameters.Parameters
import com.affise.attribution.internal.StoreInternalEventUseCase
import com.affise.attribution.internal.predefined.SessionStartInternalEvent
import com.affise.attribution.utils.delayRun
import com.affise.attribution.utils.timestamp
import java.util.*

data class SessionData(
    val lifetimeSessionCount: Long = 0,
    val affiseSessionCount: Long = 0
)

internal class SessionManagerImpl(
    private val preferences: SharedPreferences,
    private val activityCountProvider: CurrentActiveActivityCountProvider,
    private val internalEventUseCase: StoreInternalEventUseCase
) : SessionManager {

    private var sessionData: SessionData = SessionData(
        preferences.getLong(Parameters.LIFETIME_SESSION_COUNT, 0L),
        preferences.getLong(Parameters.AFFISE_SESSION_COUNT, 0L)
    )

    /**
     * Time of start session
     */
    private var openAppTime: Long? = null

    /**
     * Last date time of user active
     */
    private var closeAppDateTime: Long? = null

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
                sessionStart()
            } else {
                //Update session status if need
                checkSessionToStart()

                //Save date time of user quit or hide app
                closeAppDateTime = timestamp()

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

    override fun sessionStart() {
        //App is open
        isOpenApp = true

        /**
         * Check create open app time
         */
        if (openAppTime == null) {
            //open app time
            openAppTime = SystemClock.elapsedRealtime()
        }

        delayRun(TIME_TO_START_SESSION) {
            if (sessionTime() == 0L) return@delayRun
            //Send sdk events
            internalEventUseCase.storeInternalEvent(
                SessionStartInternalEvent(
                    affiseSessionCount = getSessionCount(),
                    lifetimeSessionCount = getLifetimeSessionTime()
                )
            )
        }
    }

    /**
     * Getting the last time the user was in the application
     * @return time
     */
    override fun getLastInteractionTime() = when {
        //Current time if app is open
        isOpenApp -> timestamp()
        //lastInteractionTime is session is active
        else -> closeAppDateTime
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

        //if session started
        if (sessionTime() > 0) {
            sessionActive = true

            //Save new session
            addNewSession()
        }
    }

    private fun sessionTime(): Long {
        if (sessionActive) return 0

        //Check open app time
        openAppTime?.let { startTime ->
            //Time current session
            val time = SystemClock.elapsedRealtime() - startTime - TIME_TO_START_SESSION

            //if session started
            if (time > 0) {
                return time
            }
        }
        return 0
    }

    /**
     * Save session time
     */
    @SuppressLint("ApplySharedPref")
    private fun saveSessionTime() {
        val lifetimeSessionTime = getLifetimeSessionTime()

        sessionData = sessionData.copy(lifetimeSessionCount = lifetimeSessionTime)

        preferences
            .edit()
            .putLong(Parameters.LIFETIME_SESSION_COUNT, lifetimeSessionTime)
            .commit()
    }

    /**
     * Get all old sessions time
     */
    private fun getSaveSessionsTime() = sessionData.lifetimeSessionCount

    /**
     * Save new session count
     */
    @SuppressLint("ApplySharedPref")
    private fun addNewSession() {
        val count = sessionData.affiseSessionCount + 1

        sessionData = sessionData.copy(affiseSessionCount = count)

        preferences
            .edit()
            .putLong(Parameters.AFFISE_SESSION_COUNT, count)
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

        return sessionData.affiseSessionCount
    }

    /**
     * Get current session time
     */
    override fun getSessionTime() = openAppTime?.let { startTime ->
        SystemClock.elapsedRealtime() - startTime
    } ?: 0

    /**
     * Get all sessions time
     */
    override fun getLifetimeSessionTime() = getSaveSessionsTime() + getSessionTime()

    companion object {
        private const val TIME_TO_START_SESSION = 15 * 1000L
    }
}