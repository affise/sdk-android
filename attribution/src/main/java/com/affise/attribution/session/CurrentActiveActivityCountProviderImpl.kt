package com.affise.attribution.session

import com.affise.attribution.utils.ActivityActionsManager
import com.affise.attribution.utils.ActivityLifecycleCallback

/**
 * Provider for change activities count
 */
internal class CurrentActiveActivityCountProviderImpl(
    private val activityActionsManager: ActivityActionsManager
) : CurrentActiveActivityCountProvider {
    /**
     * Count of open activity
     */
    private var activityCount: Long = 0

    /**
     * Listener of change count of open activity
     */
    private var activityCountListener: MutableList<((count: Long) -> Unit)> = mutableListOf()

    /**
     * Listener of start activity
     */
    private var onStartedSubscription: ActivityLifecycleCallback? = null

    /**
     * Listener of stop activity
     */
    private var onStoppedSubscription: ActivityLifecycleCallback? = null

    /**
     * Start provider
     */
    @Synchronized
    override fun init() {
        if (onStartedSubscription == null) {
            onStartedSubscription = ActivityLifecycleCallback { _ ->
                //Update open activity count
                activityCount += 1

                //Notify new count
                activityCountListener.forEach {
                    it.invoke(activityCount)
                }
            }.apply {
                activityActionsManager.addOnActivityStartedListener(this)
            }
        }

        if (onStoppedSubscription == null) {
            onStoppedSubscription = ActivityLifecycleCallback { _ ->
                //Update open activity count
                if (activityCount > 0) {
                    activityCount -= 1
                }

                //Notify new count
                activityCountListener.forEach {
                    it.invoke(activityCount)
                }
            }.apply {
                activityActionsManager.addOnActivityStoppedListener(this)
            }
        }
    }

    /**
     * Add [listener] for change activity count
     */
    override fun addActivityCountListener(listener: (count: Long) -> Unit) {
        activityCountListener.add(listener)
    }

    /**
     * Get current open activities count
     */
    override fun getActivityCount(): Long = activityCount
}