package com.affise.attribution.internal

import android.app.Application
import com.affise.attribution.Affise
import com.affise.attribution.events.autoCatchingClick.toAutoCatchingType
import com.affise.attribution.internal.builder.AffiseBuilder
import com.affise.attribution.internal.callback.AffiseResult
import com.affise.attribution.internal.callback.OnAffiseCallback
import com.affise.attribution.internal.events.EventFactory
import com.affise.attribution.internal.ext.addSettings
import com.affise.attribution.internal.ext.getAppIdAndSecretKey
import com.affise.attribution.internal.ext.isValid
import com.affise.attribution.internal.ext.opt
import com.affise.attribution.internal.ext.toListOfMap
import com.affise.attribution.internal.platform.InternalCrossPlatform
import com.affise.attribution.internal.utils.jsonToMap
import com.affise.attribution.internal.utils.toJSONObject
import com.affise.attribution.modules.toAffiseModules
import com.affise.attribution.referrer.toReferrerKey
import org.json.JSONObject


class AffiseApiWrapper(private val app: Application?) {

    companion object {
        private const val UUID = "callback_uuid"
    }

    private val factory: EventFactory = EventFactory()
    private val affiseBuilder: AffiseBuilder = AffiseBuilder()

    private var callback: ((AffiseApiMethod, Map<String, Any?>) -> Map<String, Any?>?)? = null

    fun setCallback(callback: OnAffiseCallback? = null) {
        this.callback = { name, data ->
            val result = callback?.handleCallback(name.method, JSONObject(data).toString())
            jsonToMap(result)
        }
    }

    fun setCallback(callback: ((String, Map<String, Any?>) -> Map<String, Any?>?)? = null) {
        this.callback = { name, data ->
            callback?.invoke(name.method, data)
        }
    }

    fun handleDeeplink(uri: String?) {
        InternalCrossPlatform.deeplink(uri ?: "")
    }

    fun react() {
        InternalCrossPlatform.react()
    }

    fun flutter() {
        InternalCrossPlatform.flutter()
    }

    fun unity() {
        InternalCrossPlatform.unity()
    }

    fun call(api: AffiseApiMethod?, map: Map<String, *>, result: AffiseResult) {
        when (api) {
            AffiseApiMethod.INIT ->
                callInit(api, map, result)

            AffiseApiMethod.IS_INITIALIZED ->
                callIsInitialized(api, map, result)

            AffiseApiMethod.SEND_EVENT ->
                callSendEvent(api, map, result)

            AffiseApiMethod.SEND_EVENT_NOW ->
                callSendEventNow(api, map, result)

            AffiseApiMethod.ADD_PUSH_TOKEN ->
                callAddPushToken(api, map, result)

//            AffiseApiMethod.REGISTER_WEB_VIEW ->
//                callRegisterWebView(api, map, result)

//            AffiseApiMethod.UNREGISTER_WEB_VIEW ->
//                callUnregisterWebView(api, map, result)

            AffiseApiMethod.SET_SECRET_ID ->
                callSetSecretId(api, map, result)

            AffiseApiMethod.SET_AUTO_CATCHING_TYPES ->
                callSetAutoCatchingTypes(api, map, result)

            AffiseApiMethod.SET_OFFLINE_MODE_ENABLED ->
                callSetOfflineModeEnabled(api, map, result)

            AffiseApiMethod.IS_OFFLINE_MODE_ENABLED ->
                callIsOfflineModeEnabled(api, map, result)

            AffiseApiMethod.SET_BACKGROUND_TRACKING_ENABLED ->
                callSetBackgroundTrackingEnabled(api, map, result)

            AffiseApiMethod.IS_BACKGROUND_TRACKING_ENABLED ->
                callIsBackgroundTrackingEnabled(api, map, result)

            AffiseApiMethod.SET_TRACKING_ENABLED ->
                callSetTrackingEnabled(api, map, result)

            AffiseApiMethod.IS_TRACKING_ENABLED ->
                callIsTrackingEnabled(api, map, result)

            AffiseApiMethod.FORGET ->
                callForget(api, map, result)

            AffiseApiMethod.SET_ENABLED_METRICS ->
                callSetEnabledMetrics(api, map, result)

            AffiseApiMethod.CRASH_APPLICATION ->
                callCrashApplication(api, map, result)

            AffiseApiMethod.GET_RANDOM_USER_ID ->
                callGetRandomUserId(api, map, result)

            AffiseApiMethod.GET_RANDOM_DEVICE_ID ->
                callGetRandomDeviceId(api, map, result)

            AffiseApiMethod.GET_PROVIDERS ->
                callGetProviders(api, map, result)

            AffiseApiMethod.MODULE_START ->
                callModuleStart(api, map, result)

            AffiseApiMethod.GET_MODULES_INSTALLED ->
                callGetModulesInstalled(api, map, result)

            AffiseApiMethod.IS_FIRST_RUN ->
                callIsFirstRun(api, map, result)

            AffiseApiMethod.GET_REFERRER_CALLBACK ->
                callGetReferrer(api, map, result)

            AffiseApiMethod.GET_REFERRER_VALUE_CALLBACK ->
                callGetReferrerValue(api, map, result)

            AffiseApiMethod.GET_STATUS_CALLBACK ->
                callGetStatusCallback(api, map, result)

            AffiseApiMethod.REGISTER_DEEPLINK_CALLBACK ->
                callRegisterDeeplinkCallback(api, map, result)

            AffiseApiMethod.DEBUG_VALIDATE_CALLBACK ->
                callDebugValidateCallback(api, map, result)

            AffiseApiMethod.DEBUG_NETWORK_CALLBACK ->
                callDebugNetworkCallback(api, map, result)

            AffiseApiMethod.AFFISE_BUILDER ->
                callBuilder(api, map, result)

            else -> result.notImplemented()
        }
    }

    private fun callInit(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        if (app == null) {
            result.error("api [${api.method}]: no application context")
            return
        }

        val properties = map.opt<Map<*, *>>(api)

        if (properties == null) {
            result.error("api [${api.method}]: no valid AffiseInitProperties")
            return
        }

        if (!properties.isValid()) {
            result.error("api [${api.method}]: affiseAppId or secretKey is not set")
            return
        }

        val (affiseAppId, secretKey) = properties.getAppIdAndSecretKey()

        Affise
            .settings(
                affiseAppId = affiseAppId,
                secretKey = secretKey
            )
            .addSettings(properties)
            .start(app)

        InternalCrossPlatform.start()
        result.success(null)
    }

    private fun callIsInitialized(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        Affise.isInitialized().let {
            result.success(it)
        }
    }

    private fun callSendEvent(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        val data = map.opt<Map<*, *>>(api)
        if (data == null) {
            result.error("api [${api.method}]: value not set")
            return
        }

        val event = factory.create(data)
        if (event != null) {
            event.send()
            result.success(null)
        } else {
            result.error("api [${api.method}]: not valid event ${map.toJSONObject()}")
        }
    }

    private fun callSendEventNow(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        val data = map.opt<Map<*, *>>(api)
        if (data == null) {
            result.error("api [${api.method}]: value not set")
            return
        }

        val event = factory.create(data)
        if (event != null) {
            event.sendNow({
                val uuid = map.opt<String>(UUID)
                val dataMap = mapOf<String, Any?>(
                    UUID to uuid,
                )
                callback?.invoke(api, dataMap)
            }) { response ->
                val uuid = map.opt<String>(UUID)
                val dataMap = mapOf<String, Any?>(
                    UUID to uuid,
                    api.method to mapOf(
                        "response" to mapOf(
                            "code" to response.code,
                            "message" to response.message,
                            "body" to response.body,
                        ),
                    ),
                )
                // return true to save event on error
                return@sendNow callback?.invoke(api, dataMap)?.opt<Boolean>(api) ?: true
            }
            result.success(null)
        } else {
            result.error("api [${api.method}]: not valid event ${map.toJSONObject()}")
        }
    }

    private fun callAddPushToken(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        val pushToken = map.opt<String>(api)
        if (pushToken.isNullOrBlank()) {
            result.error("api [${api.method}]: value not set")
        } else {
            Affise.addPushToken(pushToken)
            result.success(null)
        }
    }

    private fun callRegisterWebView(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
//        Affise.registerWebView()
//        result.success(null)
        result.notImplemented()
    }

    private fun callUnregisterWebView(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        Affise.unregisterWebView()
        result.success(null)
    }

    private fun callSetSecretId(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        val secretKey = map.opt<String>(api)
        if (secretKey.isNullOrBlank()) {
            result.error("api [${api.method}]: value not set")
        } else {
            Affise.setSecretId(secretKey)
            result.success(null)
        }
    }

    private fun callSetAutoCatchingTypes(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        val list = map.opt<List<*>>(api)
        val types = list?.mapNotNull {
            it?.toString()?.toAutoCatchingType()
        }
        Affise.setAutoCatchingTypes(types)
        result.success(null)
    }

    private fun callSetOfflineModeEnabled(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        val enabled = map.opt<Boolean>(api)
        if (enabled == null) {
            result.error("api [${api.method}]: value not set")
        } else {
            Affise.setOfflineModeEnabled(enabled)
            result.success(null)
        }
    }

    private fun callIsOfflineModeEnabled(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        Affise.isOfflineModeEnabled().let {
            result.success(it)
        }
    }

    private fun callSetBackgroundTrackingEnabled(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        val enabled = map.opt<Boolean>(api)
        if (enabled == null) {
            result.error("api [${api.method}]: value not set")
        } else {
            Affise.setBackgroundTrackingEnabled(enabled)
            result.success(null)
        }
    }

    private fun callIsBackgroundTrackingEnabled(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        Affise.isBackgroundTrackingEnabled().let {
            result.success(it)
        }
    }

    private fun callSetTrackingEnabled(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        val enabled = map.opt<Boolean>(api)
        if (enabled == null) {
            result.error("api [${api.method}]: value not set")
        } else {
            Affise.setTrackingEnabled(enabled)
            result.success(null)
        }
    }

    private fun callIsTrackingEnabled(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        Affise.isTrackingEnabled().let {
            result.success(it)
        }
    }

    private fun callForget(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        val userData = map.opt<String>(api)
        if (userData.isNullOrBlank()) {
            result.error("api [${api.method}]: value not set")
        } else {
            Affise.forget(userData)
            result.success(null)
        }
    }

    private fun callSetEnabledMetrics(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        val enabled = map.opt<Boolean>(api)
        if (enabled == null) {
            result.error("api [${api.method}]: value not set")
        } else {
            Affise.setEnabledMetrics(enabled)
            result.success(null)
        }
    }

    private fun callCrashApplication(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        Affise.crashApplication()
        result.success(null)
    }

    private fun callGetRandomUserId(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        result.success(Affise.getRandomUserId())
    }

    private fun callGetRandomDeviceId(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        result.success(Affise.getRandomDeviceId())
    }

    private fun callGetProviders(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        result.success(Affise.getProviders().entries.associate { it.key.provider to it.value })
    }

    private fun callModuleStart(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        val module = map.opt<String>(api)?.toAffiseModules()
        if (module == null) {
            result.error("api [${api.method}]: no valid AffiseModules")
        } else {
            result.success(Affise.moduleStart(module))
        }
    }

    private fun callGetModulesInstalled(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        result.success(Affise.getModulesInstalled().map { it.name })
    }

    private fun callIsFirstRun(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        result.success(Affise.isFirstRun())
    }

    private fun callGetReferrer(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        val uuid = map.opt<String>(UUID)
        Affise.getReferrer { referrer ->
            val data = mapOf<String, Any?>(
                UUID to uuid,
                api.method to referrer,
            )
            callback?.invoke(api, data)
        }
        result.success(null)
    }

    private fun callGetReferrerValue(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        val uuid = map.opt<String>(UUID)
        val key = map.opt<String>(api)?.toReferrerKey()

        if (key == null) {
            result.error("api [${api.method}]: value not set")
        } else {
            Affise.getReferrerValue(key) { referrer ->
                val data = mapOf<String, Any?>(
                    UUID to uuid,
                    api.method to referrer,
                )
                callback?.invoke(api, data)
            }
            result.success(null)
        }
    }

    private fun callGetStatusCallback(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        val uuid = map.opt<String>(UUID)
        val module = map.opt<String>(api)?.toAffiseModules()
        if (module == null) {
            result.error("api [${api.method}]: value not set")
        } else {
            Affise.getStatus(module) {
                val data = mapOf<String, Any?>(
                    UUID to uuid,
                    api.method to it.toListOfMap(),
                )
                callback?.invoke(api, data)
            }
            result.success(null)
        }
    }

    private fun callRegisterDeeplinkCallback(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        val uuid = map.opt<String>(UUID)
        Affise.registerDeeplinkCallback { uri ->
            val data = mapOf<String, Any?>(
                UUID to uuid,
                api.method to uri.toString(),
            )
            callback?.invoke(api, data)
            true
        }
        result.success(null)
    }

    private fun callDebugValidateCallback(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        val uuid = map.opt<String>(UUID)
        Affise.Debug.validate {
            val data = mapOf<String, Any?>(
                UUID to uuid,
                api.method to it.status,
            )
            callback?.invoke(api, data)
        }
        result.success(null)
    }

    private fun callDebugNetworkCallback(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        Affise.Debug.network { request, response ->
            val data = mapOf<String, Any?>(
                api.method to mapOf(
                    "request" to mapOf(
                        "method" to request.method.toString(),
                        "url" to request.url.toString(),
                        "headers" to request.headers,
                        "body" to request.body,
                    ),
                    "response" to mapOf(
                        "code" to response.code,
                        "message" to response.message,
                        "body" to response.body,
                    ),
                ),
            )
            callback?.invoke(api, data)
        }
        result.success(null)
    }

    private fun callBuilder(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        affiseBuilder.call(api, map, result)
    }
}