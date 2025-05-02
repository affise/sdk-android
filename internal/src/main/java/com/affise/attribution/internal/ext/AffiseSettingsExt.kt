package com.affise.attribution.internal.ext

import com.affise.attribution.events.autoCatchingClick.AutoCatchingType
import com.affise.attribution.events.autoCatchingClick.toAutoCatchingType
import com.affise.attribution.settings.AffiseConfig
import com.affise.attribution.settings.AffiseSettings

internal object Field {
    const val AFFISE_APP_ID = "affiseAppId"
    const val PART_PARAM_NAME = "partParamName"
    const val PART_PARAM_NAME_TOKEN = "partParamNameToken"
    const val APP_TOKEN = "appToken"
    const val SECRET_KEY = "secretKey"
    const val AUTO_CATCHING_CLICK_EVENTS = "autoCatchingClickEvents"
    const val IS_PRODUCTION = "isProduction"
    const val ENABLED_METRICS = "enabledMetrics"
    const val DOMAIN = "domain"
    const val CONFIG_STRINGS = "configStrings"
}

internal fun Map<*, *>.getString(key: String): String? = this[key]?.toString()
internal fun Map<*, *>.getBoolean(key: String): Boolean? = this.getString(key)?.toBoolean()
internal fun Map<*, *>.getList(key: String): List<*>? = this[key] as? List<*>
internal fun Map<*, *>.getMap(key: String): Map<*,*>? = this[key] as? Map<*,*>
internal fun List<*>.toAutoCatchingType(): List<AutoCatchingType> = this.mapNotNull {
    it?.toString()?.toAutoCatchingType()
}

internal fun Map<*, *>.isValid(): Boolean {
    if (this.getString(Field.AFFISE_APP_ID).isNullOrBlank()) return false
    if (this.getString(Field.SECRET_KEY).isNullOrBlank()) return false
    return true
}

internal fun Map<*, *>.getAppIdAndSecretKey(): Pair<String, String> = Pair(
    this.getString(Field.AFFISE_APP_ID) ?: "",
    this.getString(Field.SECRET_KEY) ?: "",
)

internal fun AffiseSettings.addSettings(map: Map<*, *>): AffiseSettings = this.also { settings ->
    map.getString(Field.DOMAIN)?.let {
        settings.setDomain(it)
    }
    map.getBoolean(Field.IS_PRODUCTION)?.let {
        settings.setProduction(it)
    }
    map.getString(Field.PART_PARAM_NAME)?.let {
        settings.setPartParamName(it)
    }
    map.getString(Field.PART_PARAM_NAME_TOKEN)?.let {
        settings.setPartParamNameToken(it)
    }
    map.getString(Field.APP_TOKEN)?.let {
        settings.setAppToken(it)
    }
    map.getMap(Field.CONFIG_STRINGS)?.let {
        for ((key, value) in it) {
            value ?: continue
            AffiseConfig.from(key.toString())?.let { configKey ->
                settings.setConfigValue(configKey, value)
            }
        }
    }
//    map.getList(Field.AUTO_CATCHING_CLICK_EVENTS)?.toAutoCatchingType()?.let {
//        settings.setAutoCatchingClickEvents(it)
//    }
//    map.getBoolean(Field.ENABLED_METRICS)?.let {
//        settings.setEnabledMetrics(it)
//    }
}
