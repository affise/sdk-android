package com.affise.attribution.utils

import android.app.Activity
import android.view.View

/**
 * An interface describing a callback that will be fired when activity has a lifecycle event
 */
internal fun interface ActivityLifecycleCallback {
    /**
     * Triggered when a new [Activity] has received an lifecycle event
     */
    fun handle(activity: Activity)
}

/**
 * An interface describing a callback that will be fired when a click event occurs on the Activity
 */
internal fun interface ActivityClickCallback {
    /**
     * Triggered when a new [Activity] has received a click event
     */
    fun handle(activity: Activity, view: View)
}

/**
 * Manager for handling events occurring on the activity
 */
internal interface ActivityActionsManager {

    /**
     * Add [listener] for start activities
     */
    fun addOnActivityStartedListener(listener: ActivityLifecycleCallback)

    /**
     * Add [listener] for resume activities
     */
    fun addOnActivityResumedListener(listener: ActivityLifecycleCallback)

    /**
     * Add [listener] for stop activities
     */
    fun addOnActivityStoppedListener(listener: ActivityLifecycleCallback)

    /**
     * Add [listener] for clicks on activities
     */
    fun addOnActivityClickListener(listener: ActivityClickCallback)
}