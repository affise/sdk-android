package com.affise.attribution

interface AffiseUnityPluginApi {
    fun getInstalledTime(): Long
    fun getInstalledBeginTime(): Long
    fun getApiVersion(): String
    fun getOSVersion(): String
    fun getIsp(): String
    fun getAndroidId(): String
    fun getDeviceManufacturer(): String
    fun getConnectionType(): String
    fun getNetworkType(): String
    fun getStore(): String
    fun getGaidAdid(): String
    fun getReferrer(): String
}