package com.affise.attribution.session

/**
 * Manager Session interface
 */
interface SessionManager {

    /**
     * Init Manager
     */
    fun init()

    /**
     * Get session active status
     *
     * @return session is active or not
     */
    fun isSessionActive(): Boolean

    /**
     * Get last interaction time
     *
     * @return Last interaction time
     */
    fun getLastInteractionTime(): Long?

    /**
     * Get session time
     *
     * @return session time
     */
    fun getSessionTime(): Long

    /**
     * Get lifetime session time
     *
     * @return lifetime session time
     */
    fun getLifetimeSessionTime(): Long

    /**
     * Get session count
     *
     * @return session count
     */
    fun getSessionCount(): Long

    fun sessionStart()
}