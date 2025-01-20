package com.affise.attribution.referrer

import com.affise.attribution.modules.AffiseModuleManager
import com.affise.attribution.modules.AffiseModules
import com.affise.attribution.utils.getReferrerValue

class RetrieveReferrerOnServerUseCase(
    private val moduleManager: AffiseModuleManager,
) {
    private var referrerCallback: ReferrerCallback? = null

    private fun getReferrerModule(): ReferrerCallback? {
        if (referrerCallback != null) return referrerCallback
        val module = moduleManager.getModule(AffiseModules.Status)
        referrerCallback = module as? ReferrerCallback
        return referrerCallback
    }

    private fun handleReferrerOnServer(callback: OnReferrerCallback) {
        getReferrerModule()?.getReferrer(callback) ?: callback.handleReferrer(null)
    }

    fun getReferrerOnServer(callback: OnReferrerCallback?) {
        handleReferrerOnServer {
            callback?.handleReferrer(it)
        }
    }

    fun getReferrerOnServerValue(key: ReferrerKey, callback: OnReferrerCallback?) {
        handleReferrerOnServer {
            callback?.handleReferrer(it?.getReferrerValue(key))
        }
    }
}