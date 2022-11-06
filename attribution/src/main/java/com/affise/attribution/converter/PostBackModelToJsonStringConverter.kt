package com.affise.attribution.converter

import com.affise.attribution.network.entity.PostBackModel
import com.affise.attribution.parameters.Parameters
import org.json.JSONArray
import org.json.JSONObject

/**
 * Converter List<PostBackModel> to String
 */
class PostBackModelToJsonStringConverter : Converter<List<PostBackModel>, String> {

    /**
     * Convert [from] list of PostBackModel to json string
     */
    override fun convert(from: List<PostBackModel>): String {
        //Create jsonArray
        val jsonArray = JSONArray()

        //Create jsonObject for all objects in list
        from.forEach { model ->
            val jsonObject = parameters(model)
            jsonArray.put(jsonObject)
        }

        //Create json string
        return jsonArray
            .toString()
            .replace("\\/", "/")
    }

    /**
     * Create parameters map of object
     */
    private fun parameters(obj: PostBackModel) = JSONObject().apply {
        put(Parameters.AFFISE_APP_ID, obj.affiseAppId)
        put(Parameters.AFFISE_PKG_APP_NAME, obj.affisePkgAppName)
        //affise_app_name_dashboard
        put(Parameters.APP_VERSION, obj.appVersion)
        put(Parameters.APP_VERSION_RAW, obj.appVersionRaw)
        put(Parameters.STORE, obj.store)
        //tracker_token
        //tracker_name
        //first_tracker_token
        //first_tracker_name
        //last_tracker_token
        //last_tracker_name
        //outdated_tracker_token
        put(Parameters.INSTALLED_TIME, obj.installedTime)
        put(Parameters.FIRST_OPEN_TIME, obj.firstOpenTime)
        put(Parameters.INSTALLED_HOUR, obj.installedHour)
        put(Parameters.FIRST_OPEN_HOUR, obj.firstOpenHour)
        put(Parameters.INSTALL_FIRST_EVENT, obj.installFirstEvent)
        put(Parameters.INSTALL_BEGIN_TIME, obj.installBeginTime)
        put(Parameters.INSTALL_FINISH_TIME, obj.installFinishTime)
        put(Parameters.REFERRER_INSTALL_VERSION, obj.referrerInstallVersion)
        put(Parameters.REFERRAL_TIME, obj.referralTime)
        put(Parameters.REFERRER_CLICK_TIME, obj.referrerClickTimestamp)
        put(Parameters.REFERRER_CLICK_TIME_SERVER, obj.referrerClickTimestampServer)
        put(Parameters.REFERRER_GOOGLE_PLAY_INSTANT, obj.referrerGooglePlayInstant)
        put(Parameters.CREATED_TIME, obj.createdTime)
        put(Parameters.CREATED_TIME_MILLI, obj.createdTimeMilli)
        put(Parameters.CREATED_TIME_HOUR, obj.createdTimeHour)
        //uninstall_time
        //reinstall_time
        put(Parameters.LAST_SESSION_TIME, obj.lastSessionTime)
        put(Parameters.CONNECTION_TYPE, obj.connectionType)
        put(Parameters.CPU_TYPE, obj.cpuType)
        put(Parameters.HARDWARE_NAME, obj.hardwareName)
        put(Parameters.NETWORK_TYPE, obj.networkType)
        put(Parameters.DEVICE_MANUFACTURER, obj.deviceManufacturer)
        put(Parameters.PROXY_IP_ADDRESS, obj.proxyIpAddress)
        put(Parameters.DEEPLINK_CLICK, obj.deeplinkClick)
        put(Parameters.DEVICE_ATLAS_ID, obj.deviceAtlasId)
        put(Parameters.AFFISE_DEVICE_ID, obj.affDeviceId)
        put(Parameters.AFFISE_ALT_DEVICE_ID, obj.affaltDeviceId)
        put(Parameters.ADID, obj.adid)
        put(Parameters.ANDROID_ID, obj.androidId)
        put(Parameters.ANDROID_ID_MD5, obj.androidIdMd5)
        put(Parameters.MAC_SHA1, obj.macSha1)
        put(Parameters.MAC_MD5, obj.macMd5)
        put(Parameters.GAID_ADID, obj.gaidAdid)
        put(Parameters.GAID_ADID_MD5, obj.gaidAdidMd5)
        put(Parameters.OAID, obj.oaid)
        put(Parameters.OAID_MD5, obj.oaidMd5)
        put(Parameters.ALTSTR_ADID, obj.altstrAdid)
        put(Parameters.FIREOS_ADID, obj.fireosAdid)
        put(Parameters.COLOROS_ADID, obj.colorosAdid)
        put(Parameters.REFTOKEN, obj.reftoken)
        put(Parameters.REFTOKENS, obj.reftokens)
        put(Parameters.REFERRER, obj.referrer)
        put(Parameters.USER_AGENT, obj.userAgent)
        put(Parameters.MCCODE, obj.mccode)
        put(Parameters.MNCODE, obj.mncode)
        put(Parameters.ISP, obj.isp)
        put(Parameters.REGION, obj.region)
        put(Parameters.COUNTRY, obj.country)
        put(Parameters.LANGUAGE, obj.language)
        put(Parameters.DEVICE_NAME, obj.deviceName)
        put(Parameters.DEVICE_TYPE, obj.deviceType)
        put(Parameters.OS_NAME, obj.osName)
        put(Parameters.PLATFORM, obj.platform)
        put(Parameters.API_LEVEL_OS, obj.apiLevelOs)
        put(Parameters.AFFISE_SDK_VERSION, obj.affSdkVersion)
        put(Parameters.OS_VERSION, obj.osVersion)
        put(Parameters.RANDOM_USER_ID, obj.randomUserId)
        put(Parameters.AFFISE_SDK_POS, obj.affSdkPos)
        put(Parameters.TIMEZONE_DEV, obj.timezoneDev)
        put(Parameters.AFFISE_EVENT_TOKEN, obj.affEventToken)
        put(Parameters.AFFISE_EVENT_NAME, obj.affEventName)
        put(Parameters.LAST_TIME_SESSION, obj.lastTimeSession)
        put(Parameters.TIME_SESSION, obj.timeSession)
        put(Parameters.AFFISE_SESSION_COUNT, obj.affSessionCount)
        put(Parameters.LIFETIME_SESSION_COUNT, obj.lifetimeSessionCount)
        put(Parameters.AFFISE_DEEPLINK, obj.affDeeplink)
        put(Parameters.AFFISE_PART_PARAM_NAME, obj.affpartParamName)
        put(Parameters.AFFISE_PART_PARAM_NAME_TOKEN, obj.affpartParamNameToken)
        put(Parameters.AFFISE_APP_TOKEN, obj.affAppToken)
        put(Parameters.LABEL, obj.label)
        //put(Parameters.AFFISE_SDK_SECRET_ID, obj.affsdkSecretId)
        put(Parameters.UUID, obj.uuid)
        //affise_app_opened
        put(Parameters.PUSHTOKEN, obj.pushtoken)

        //Events
        val eventsArray = JSONArray()
        obj.events?.forEach { event ->
            eventsArray.put(event.data)
        }
        put(Parameters.AFFISE_EVENTS_COUNT, eventsArray.length())
        put(EVENTS_KEY, eventsArray)

        //Logs
        val logsArray = JSONArray()
        obj.logs?.forEach { log ->
            logsArray.put(log.data)
        }
        put(Parameters.AFFISE_SDK_EVENTS_COUNT, logsArray.length())
        put(SDK_EVENTS_KEY, logsArray)

        //Metrics
        val metricsArray = JSONArray()
        obj.metrics?.forEach { metric ->
            metricsArray.put(metric.data)
        }
        put(Parameters.AFFISE_METRICS_EVENTS_COUNT, metricsArray.length())
        put(METRICS_EVENTS_KEY, metricsArray)
    }

    companion object {
        private const val EVENTS_KEY = "events"
        private const val SDK_EVENTS_KEY = "sdk_events"
        private const val METRICS_EVENTS_KEY = "metrics_events"
    }
}