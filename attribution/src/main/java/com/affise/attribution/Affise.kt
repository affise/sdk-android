package com.affise.attribution

import android.app.Application
import android.webkit.WebView
import com.affise.attribution.deeplink.OnDeeplinkCallback
import com.affise.attribution.events.Event
import com.affise.attribution.referrer.ReferrerKey
import com.affise.attribution.events.autoCatchingClick.AutoCatchingType
import com.affise.attribution.events.predefined.GDPREvent
import com.affise.attribution.init.AffiseInitProperties
import com.affise.attribution.internal.InternalEvent
import com.affise.attribution.modules.AffiseModules
import com.affise.attribution.modules.OnKeyValueCallback
import com.affise.attribution.parameters.AffiseDeviceIdProvider
import com.affise.attribution.parameters.PushTokenProvider
import com.affise.attribution.parameters.RandomUserIdProvider
import com.affise.attribution.referrer.OnReferrerCallback

/**
 * Entry point to initialise Affise Attribution library
 */
object Affise {

    /**
     * Api to communication with Affise
     */
    private var api: AffiseApi? = null

    /**
     * Init [AffiseComponent] with [app] and [initProperties]
     */
    @JvmStatic
    @Synchronized
    fun init(
        app: Application,
        initProperties: AffiseInitProperties
    ) {
        //Check creating AffiseComponent
        if (api == null) {
            //Create AffiseComponent
            api = AffiseComponent(app, initProperties)
        }
    }

    @JvmStatic
    fun isInitialized(): Boolean {
        return api?.isInitialized() ?: false
    }

    /**
     * Send events
     */
    @JvmStatic
    fun sendEvents() {
        api?.eventsManager?.sendEvents()
    }

    /**
     * Store and send [event]
     */
    @JvmStatic
    fun sendEvent(event: Event) {
        api?.storeEventUseCase?.storeEvent(event)
    }

    /**
     * Add [pushToken]
     */
    @JvmStatic
    fun addPushToken(pushToken: String) {
        api?.sharedPreferences?.edit()?.let {
            it.putString(PushTokenProvider.KEY_APP_PUSHTOKEN, pushToken)
            it.commit()
        }
    }

    /**
     * Register [webView] to WebBridge
     */
    @JvmStatic
    fun registerWebView(webView: WebView) {
        api?.webBridgeManager?.registerWebView(webView)
    }

    /**
     * Unregister webView on WebBridge
     */
    @JvmStatic
    fun unregisterWebView() {
        api?.webBridgeManager?.unregisterWebView()
    }

    /**
     * Register [callback] for deeplink
     */
    @JvmStatic
    fun registerDeeplinkCallback(callback: OnDeeplinkCallback) {
        api?.deeplinkManager?.setDeeplinkCallback(callback)
    }

    /**
     * Set new SDK Secret Key
     */
    @JvmStatic
    fun setSecretId(secretKey: String) {
        api?.initPropertiesStorage?.updateSecretKey(secretKey)
    }

    /**
     * Send enabled autoCatching types
     */
    @JvmStatic
    fun setAutoCatchingTypes(types: List<AutoCatchingType>?) {
        api?.autoCatchingClickProvider?.setTypes(types)
    }

    /**
     * Sets offline mode to [enabled] state
     *
     * When enabled, no network activity should be triggered by this library,
     * but background work is not paused. When offline mode is enabled,
     * all recorded events should be sent
     */
    @JvmStatic
    fun setOfflineModeEnabled(enabled: Boolean) {
        api?.preferencesUseCase?.setOfflineModeEnabled(enabled)
    }

    /**
     * Returns current offline mode state
     */
    @JvmStatic
    fun isOfflineModeEnabled(): Boolean = api?.preferencesUseCase?.isOfflineModeEnabled() ?: false

    /**
     * Sets background tracking mode to [enabled] state
     *
     * When disabled, library should not generate any tracking events while in background
     */
    @JvmStatic
    fun setBackgroundTrackingEnabled(enabled: Boolean) {
        api?.preferencesUseCase?.setBackgroundTrackingEnabled(enabled)
    }

    /**
     * Returns current background tracking state
     */
    @JvmStatic
    fun isBackgroundTrackingEnabled(): Boolean =
        api?.preferencesUseCase?.isBackgroundTrackingEnabled() ?: false

    /**
     * Sets offline mode to [enabled] state
     *
     * When disabled, library should not generate any tracking events
     */
    @JvmStatic
    fun setTrackingEnabled(enabled: Boolean) {
        api?.preferencesUseCase?.setTrackingEnabled(enabled)
    }

    /**
     * Returns current tracking state
     */
    @JvmStatic
    fun isTrackingEnabled(): Boolean = api?.preferencesUseCase?.isTrackingEnabled() ?: false

    /**
     * Erases all user data from mobile and sends [GDPREvent]
     */
    @JvmStatic
    fun forget(userData: String) {
        api?.sendGDPREventUseCase?.registerForgetMeEvent(userData)
    }

    /**
     * Set [enabled] collect metrics
     *
     * When disabled, library should not generate any metrics events,
     * but will send the saved metrics events
     */
    @JvmStatic
    fun setEnabledMetrics(enabled: Boolean) {
        api?.metricsManager?.setEnabledMetrics(enabled)
    }

    @JvmStatic
    fun crashApplication() {
        api?.crashApplicationUseCase?.crash()
    }

    /**
     * Get referrer
     */
    @JvmStatic
    fun getReferrer(callback: OnReferrerCallback?) {
        api?.retrieveInstallReferrerUseCase?.getReferrer(callback)
    }

    /**
     * Get referrer Value
     */
    @JvmStatic
    fun getReferrerValue(key: ReferrerKey, callback: OnReferrerCallback?) {
        api?.retrieveInstallReferrerUseCase?.getReferrerValue(key, callback)
    }

    /**
     * Get module status
     */
    @JvmStatic
    fun getStatus(module: AffiseModules, onComplete: OnKeyValueCallback) {
        api?.moduleManager?.status(module, onComplete)
    }

    /**
     * Get random User Id
     */
    @JvmStatic
    fun getRandomUserId(): String? {
        return api?.postBackModelFactory?.getProvider<RandomUserIdProvider>()?.provide()
    }

    /**
     * Get random Device Id
     */
    @JvmStatic
    fun getRandomDeviceId(): String? {
        return api?.postBackModelFactory?.getProvider<AffiseDeviceIdProvider>()?.provide()
    }

    internal fun getApi(): AffiseApi? = api

    /**
     * Send internal event
     */
    @JvmStatic
    internal fun sendInternalEvent(event: InternalEvent) {
        api?.storeInternalEventUseCase?.storeInternalEvent(event)
    }
}