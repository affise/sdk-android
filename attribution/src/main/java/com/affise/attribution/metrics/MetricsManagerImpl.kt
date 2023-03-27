package com.affise.attribution.metrics

import android.app.Activity
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import com.affise.attribution.converter.StringToSHA1Converter
import com.affise.attribution.utils.ActivityActionsManager
import com.affise.attribution.utils.timestamp

/**
 * MetricsManager
 *
 * @property activityActionsManager listeners for changes activity
 * @property metricsUseCase usecase to generate metrics events
 * @property converterToSHA1 convert String to SHA1 String
 */
internal class MetricsManagerImpl(
    private val activityActionsManager: ActivityActionsManager,
    private val metricsUseCase: MetricsUseCase,
    private val converterToSHA1: StringToSHA1Converter
) : MetricsManager {

    /**
     * HashMap of all open activities
     */
    private var openActivities = HashMap<String, Long>()

    /**
     * Flag of enabled metrics
     */
    private var enabledMetrics: Boolean = false

    init {
        //Add listeners to start activities
        activityActionsManager.addOnActivityStartedListener {
            openActivity(it)
        }

        //Add listeners to stop activities
        activityActionsManager.addOnActivityStoppedListener {
            closeActivity(it)
        }

        //Add listeners to clicks on activities
        activityActionsManager.addOnActivityClickListener { activity, view ->
            //Check if enabled metrics
            if (enabledMetrics) {
                clickOnActivity(activity, view)
            }
        }
    }

    /**
     * Enabled metrics
     */
    override fun setEnabledMetrics(enabled: Boolean) {
        enabledMetrics = enabled
    }

    /**
     * Handler of open [activity]
     */
    private fun openActivity(activity: Activity) {
        //Get name of activity
        val activityName = activity.javaClass.simpleName

        //Check if activity is open
        if (!openActivities.containsKey(activityName)) {
            //Add activity to hash map with time of it opened
            openActivities[activityName] = timestamp()
        }
    }

    /**
     * Handler of close [activity]
     */
    private fun closeActivity(activity: Activity) {
        //Get name of activity
        val activityName = activity.javaClass.simpleName

        //Check if activity is open
        if (openActivities.containsKey(activityName)) {
            //Remove activity from hash map of opened activities
            openActivities.remove(activityName)?.let { startTime ->
                //Check if enabled metrics
                if (enabledMetrics) {
                    //Set data of open activity to usecase
                    metricsUseCase.addOpenActivityTime(
                        activity.javaClass.simpleName,
                        timestamp() - startTime
                    )
                }
            }
        }
    }

    /**
     * Handler of click on [view] in [activity]
     */
    private fun clickOnActivity(activity: Activity, view: View) {
        //Get name of activity
        val activityName = activity.javaClass.simpleName

        //Generate data of click
        val data = "AutoCatchingClickEvent_${getDataKey(activityName, view)}"

        //Set data of click on activity to usecase
        metricsUseCase.addClickOnActivity(activityName, data)
    }

    /**
     * Generate data of click with [activityName] and [view]
     */
    private fun getDataKey(activityName: String, view: View) = converterToSHA1
        .convert(
            getDataView(view, true)
                .fold(activityName) { acc, value ->
                    acc + value
                }
        )

    /**
     * Get data from [view], and flag if it [isRoot] view by click
     */
    private fun getDataView(view: View, isRoot: Boolean = true): List<String> = when (view) {
        //Check view if it is ViewGroup
        is ViewGroup -> {
            mutableListOf<String>().apply {
                if (isRoot) {
                    add(getViewIdName(view))
                }

                addAll(
                    (0 until view.childCount).flatMap {
                        //Get data from all child views
                        getDataView(view.getChildAt(it), false)
                    }
                )
            }
        }
        else -> listOf(getViewIdName(view))
    }

    /**
     * Get view id, or empty value if view doesn't have id
     */
    private fun getViewIdName(view: View) = view.id
        .takeIf { it != View.NO_ID }
        ?.let { id ->
            view.resources?.let { resources ->
                try {
                    resources.getResourceEntryName(id)
                } catch (e: Resources.NotFoundException) {
                    null
                }
            }
        }
        ?: ""
}