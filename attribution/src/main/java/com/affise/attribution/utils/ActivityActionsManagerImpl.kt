package com.affise.attribution.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.affise.attribution.logs.LogsManager

/**
 * Manager for handling events occurring on the activity
 *
 * @property app application on which they are listening Activities
 * @property logsManager for error logging
 */
internal class ActivityActionsManagerImpl(
    private val app: Application,
    private val logsManager: LogsManager
) : ActivityActionsManager {
    /**
     * Listeners for start activities
     */
    private val onActivityStartedListeners: MutableList<ActivityLifecycleCallback> = mutableListOf()

    /**
     * Listeners for resume activities
     */
    private val onActivityResumedListeners: MutableList<ActivityLifecycleCallback> = mutableListOf()

    /**
     * Listeners for stop activities
     */
    private val onActivityStoppedListeners: MutableList<ActivityLifecycleCallback> = mutableListOf()

    /**
     * Listeners clicks on activities
     */
    private val onActivityClickListeners: MutableList<ActivityClickCallback> = mutableListOf()

    /**
     * ActivityLifecycleCallbacks
     */
    private val callback = object : Application.ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        }

        override fun onActivityStarted(activity: Activity) {
            //Invoke all listeners for start activities
            onActivityStartedListeners.forEach {
                it.handle(activity)
            }

            //If enabled auto click event collector
            (activity.window.decorView as ViewGroup)
                .also {
                    //For all child View add listeners
                    addListeners(activity, it)

                    it.viewTreeObserver.addOnGlobalLayoutListener {
                        //For all child View add listeners
                        addListeners(activity, it)
                    }
                }
        }

        override fun onActivityResumed(activity: Activity) {
            //Invoke all listeners for resume activities
            onActivityResumedListeners.forEach {
                it.handle(activity)
            }
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
            //Invoke all listeners for stop activities
            onActivityStoppedListeners.forEach {
                it.handle(activity)
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
        }
    }

    init {
        //Register callbacks
        app.registerActivityLifecycleCallbacks(callback)
    }

    /**
     * Add [listener] for start activities
     */
    override fun addOnActivityStartedListener(listener: ActivityLifecycleCallback) {
        onActivityStartedListeners.add(listener)
    }

    /**
     * Add [listener] for resume activities
     */
    override fun addOnActivityResumedListener(listener: ActivityLifecycleCallback) {
        onActivityResumedListeners.add(listener)
    }

    /**
     * Add [listener] for stop activities
     */
    override fun addOnActivityStoppedListener(listener: ActivityLifecycleCallback) {
        onActivityStoppedListeners.add(listener)
    }

    /**
     * Add [listener] for clicks on activities
     */
    override fun addOnActivityClickListener(listener: ActivityClickCallback) {
        onActivityClickListeners.add(listener)
    }

    /**
     * Add listeners to views in [viewGroup]
     */
    private fun addListeners(activity: Activity, viewGroup: ViewGroup) {
        //For all child Views
        (0 until viewGroup.childCount).forEach {
            //Add listener
            addListener(activity, viewGroup.getChildAt(it))

            //If child view is ViewGroup
            (viewGroup.getChildAt(it) as? ViewGroup)
                ?.let { childView ->
                    //Add listeners to views
                    addListeners(activity, childView)
                }
        }
    }

    /**
     * Add listeners to [view]
     */
    @SuppressLint("DiscouragedPrivateApi", "PrivateApi")
    private fun addListener(activity: Activity, view: View) {
        try {
            //Get listenerInfo on view
            val listenerInfo = View::class.java
                .getDeclaredMethod(GET_LISTENER_INFO_DECLARED_METHOD_NAME)
                .let {
                    it.isAccessible = true
                    it.invoke(view)
                }

            //Get current onClickListener on view
            val onClickListener = Class.forName(LISTENER_INFO_CLASS_NAME)
                .getDeclaredField(ON_CLICK_LISTENER_DECLARED_FIELD_NAME)
                .also {
                    it.isAccessible = true
                }

            onClickListener.get(listenerInfo)
                ?.takeIf {
                    //Take only original View.OnClickListener
                    it is View.OnClickListener && it !is AutoCatchingOnClickListener
                }
                ?.let {
                    it as? View.OnClickListener
                }
                ?.also {
                    //Set new onClickListener with sending event
                    onClickListener.set(
                        listenerInfo,
                        AutoCatchingOnClickListener(it) { view ->
                            //Invoke all listeners for clicks on activities
                            onActivityClickListeners.forEach {
                                it.handle(activity, view)
                            }
                        }
                    )
                }
        } catch (throwable: Throwable) {
            //Add throwable into logs
            logsManager.addSdkError(throwable)
        }
    }

    companion object {
        private const val GET_LISTENER_INFO_DECLARED_METHOD_NAME = "getListenerInfo"
        private const val LISTENER_INFO_CLASS_NAME = "android.view.View\$ListenerInfo"
        private const val ON_CLICK_LISTENER_DECLARED_FIELD_NAME = "mOnClickListener"
    }
}