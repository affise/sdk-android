package com.affise.attribution.events.predefined

import org.json.JSONObject

/**
 * Base Affise log event
 *
 * @property name the type log in NETWORK, DEVICEDATA, USERDATA, SDKLOG.
 * @property value the event data.
 */
sealed class AffiseLog(val name: AffiseLogType, val value: String) {

    /**
     * Log of network errors contains [jsonObject]
     */
    class NetworkLog(val jsonObject: JSONObject) : AffiseLog(AffiseLogType.NETWORK, jsonObject.toString())

    /**
     * Log of device data errors contains any string [value]
     */
    class DevicedataLog(value: String) : AffiseLog(AffiseLogType.DEVICEDATA, value)

    /**
     * Log of user data contains any string [value]
     */
    class UserdataLog(value: String) : AffiseLog(AffiseLogType.USERDATA, value)

    /**
     * Log of sdk errors contains any string [value]
     */
    class SdkLog(value: String) : AffiseLog(AffiseLogType.SDKLOG, value)
}

/**
 * Type of log
 */
enum class AffiseLogType(val type: String) {
    NETWORK("affise_sdklog_network"),
    DEVICEDATA("affise_sdklog_ddata"),
    USERDATA("affise_sdklog_udata"),
    SDKLOG("affise_sdklog_main")
}