package com.affise.attribution.settings

import android.app.Application
import android.content.Context
import android.util.Log
import com.affise.attribution.Affise
import com.affise.attribution.events.autoCatchingClick.AutoCatchingType
import com.affise.attribution.init.AffiseInitProperties

/**
 * Affise SDK settings
 *
 * @property affiseAppId your app id
 * @property secretKey your SDK secretKey
 */
class AffiseSettings internal constructor(
    val affiseAppId: String,
    val secretKey: String,
) {
    private var domain: String? = null
    private var isProduction: Boolean = true
    private var partParamName: String? = null
    private var partParamNameToken: String? = null
    private var appToken: String? = null
    private var autoCatchingClickEvents: List<AutoCatchingType>? = null
    private var enabledMetrics: Boolean = false

    /**
     * Set Affise SDK for SandBox / Production
     */
    fun setProduction(production: Boolean): AffiseSettings = this.apply {
        this.isProduction = production
    }

    /**
     * Set Affise SDK server [domain]
     *
     * Trailing slash is irrelevant
     */
    fun setDomain(domain: String): AffiseSettings = this.apply {
        this.domain = domain
    }

    /**
     * Only for specific use case [partParamName]
     */
    fun setPartParamName(partParamName: String?): AffiseSettings = this.apply {
        this.partParamName = partParamName
    }

    /**
     * Only for specific use case [partParamNameToken]
     */
    fun setPartParamNameToken(partParamNameToken: String?): AffiseSettings = this.apply {
        this.partParamNameToken = partParamNameToken
    }

    /**
     * Set [appToken]
     */
    fun setAppToken(appToken: String?): AffiseSettings = this.apply {
        this.appToken = appToken
    }

    /**
     * Set [autoCatchingClickEvents] list of AutoCatchingType
     */
    fun setAutoCatchingClickEvents(autoCatchingClickEvents: List<AutoCatchingType>?): AffiseSettings = this.apply {
        this.autoCatchingClickEvents = autoCatchingClickEvents
    }

    /**
     * Set Metrics [enable]
     */
    fun setEnabledMetrics(enable: Boolean): AffiseSettings = this.apply {
        this.enabledMetrics = enable
    }

    private fun getInitProperties(): AffiseInitProperties = AffiseInitProperties(
        affiseAppId = affiseAppId,
        secretKey = secretKey,
        isProduction = isProduction,
        partParamName = partParamName,
        partParamNameToken = partParamNameToken,
        appToken = appToken,
        autoCatchingClickEvents = autoCatchingClickEvents,
        enabledMetrics = enabledMetrics,
        domain = domain,
    )

    /**
     * Starts Affise SDK using [context]
     */
    fun start(context: Context) {
        try {
            Affise.start(
                initProperties = getInitProperties(),
                app = context.applicationContext as Application
            )
        } catch (e: Exception) {
            Log.w(this.javaClass.simpleName, "Affise SDK start error: $e")
        }
    }
}