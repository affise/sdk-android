package com.affise.attribution.session

/**
 * Active activity count provider interface
 */
interface CurrentActiveActivityCountProvider {

    /**
     * Start provider
     */
    fun init()

    /**
     * Add [listener] to subscribe opened activity count
     */
    fun addActivityCountListener(listener: ((count: Long) -> Unit))

    /**
     * @return current foreground activity count
     */
    fun getActivityCount(): Long
}