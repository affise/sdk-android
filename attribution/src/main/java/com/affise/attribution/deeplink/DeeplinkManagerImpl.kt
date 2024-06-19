package com.affise.attribution.deeplink

import android.content.Intent
import android.net.Uri
import com.affise.attribution.init.InitPropertiesStorage
import com.affise.attribution.parameters.providers.DeeplinkClickPropertyProvider
import com.affise.attribution.utils.ActivityActionsManager
import com.affise.attribution.utils.ActivityLifecycleCallback

/**
 * Implementation for [DeeplinkManager]
 *
 * @property initProperties model to retrieve affise app id from
 * @property isDeeplinkRepository repository that stores isDeeplinkClick property, used by [DeeplinkClickPropertyProvider]
 * @property activityActionsManager listeners for changes activity
 */
internal class DeeplinkManagerImpl(
    private val initProperties: InitPropertiesStorage,
    private val isDeeplinkRepository: DeeplinkClickRepository,
    private val activityActionsManager: ActivityActionsManager
) : DeeplinkManager {

    /**
     * Listener for resume activities
     */
    private var onResumeSubscription: ActivityLifecycleCallback? = null

    /**
     * Callback that is going to be triggered when deeplink is received by application
     */
    private var deeplinkCallback: OnDeeplinkCallback? = null

    @Synchronized
    override fun init() {
        //Check started listener for resume activities
        if (onResumeSubscription == null) {
            //Create listener for resume activities
            onResumeSubscription = ActivityLifecycleCallback {
                handleIntent(it.intent)
            }.apply {
                //Add listener for resume activities
                activityActionsManager.addOnActivityResumedListener(this)
            }
        }
    }

    override fun setDeeplinkCallback(callback: OnDeeplinkCallback?) {
        deeplinkCallback = callback
    }

    override fun handleDeeplink(uri: Uri) {
        try {
            isDeeplinkRepository.setDeeplinkClick(true)
            isDeeplinkRepository.setDeeplink(uri.toString())
            deeplinkCallback?.handleDeeplink(uri.toDeeplinkValue())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun handleIntent(intent: Intent?) {
        intent ?: return
        if (intent.action != Intent.ACTION_VIEW) return
        intent.data?.let { uri ->
            handleDeeplink(uri)
        }
    }
}