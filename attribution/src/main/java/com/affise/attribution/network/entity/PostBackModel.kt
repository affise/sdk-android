package com.affise.attribution.network.entity

import com.affise.attribution.events.SerializedEvent
import com.affise.attribution.logs.SerializedLog

data class PostBackModel(
    val uuid: String,
    val affiseAppId: String,
    val affisePkgAppName: String,
    val appVersion: String,
    val appVersionRaw: String,
    val store: String,
    val installedTime: Long,
    val firstOpenTime: Long,
    val installedHour: Long,
    val firstOpenHour: Long,
    val installFirstEvent: Boolean,
    val installBeginTime: Long,
    val installFinishTime: Long,
    val referrerInstallVersion: String,
    val referralTime: Long,
    val referrerClickTimestamp: Long,
    val referrerClickTimestampServer: Long,
    val referrerGooglePlayInstant: Boolean,
    val createdTime: Long,
    val createdTimeMilli: Long,
    val createdTimeHour: Long,
    val lastSessionTime: Long,
    val connectionType: String,
    val cpuType: String,
    val hardwareName: String,
    val networkType: String,
    val deviceManufacturer: String,
    val proxyIpAddress: String,
    val deeplinkClick: Boolean,
    val deviceAtlasId: String,
    val affDeviceId: String,
    val affaltDeviceId: String,
    val adid: String,
    val androidId: String,
    val androidIdMd5: String,
    val macSha1: String,
    val macMd5: String,
    val gaidAdid: String,
    val gaidAdidMd5: String,
    val oaid: String,
    val oaidMd5: String,
    val altstrAdid: String,
    val fireosAdid: String,
    val colorosAdid: String,
    val reftoken: String,
    val reftokens: String,
    val referrer: String,
    val userAgent: String,
    val mccode: String,
    val mncode: String,
    val isp: String,
    val region: String,
    val country: String,
    val language: String,
    val deviceName: String,
    val deviceType: String,
    val osName: String,
    val platform: String,
    val apiLevelOs: String,
    val affSdkVersion: String,
    val osVersion: String,
    val randomUserId: String,
    val affSdkPos: String,
    val timezoneDev: String,
    val affEventToken: String,
    val affEventName: String,
    val lastTimeSession: Long,
    val timeSession: Long,
    val affSessionCount: Long,
    val lifetimeSessionCount: Long,
    val affDeeplink: String,
    val affpartParamName: String,
    val affpartParamNameToken: String,
    val affAppToken: String,
    val label: String,
    val affsdkSecretId: String,
    val pushtoken: String,
    val events: List<SerializedEvent>? = null,
    val logs: List<SerializedLog>? = null,
    val metrics: List<SerializedEvent>? = null
)
