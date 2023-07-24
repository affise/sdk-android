package com.affise.attribution.internal.ext

import com.affise.attribution.events.autoCatchingClick.toAutoCatchingType
import com.affise.attribution.init.AffiseInitProperties

internal object AffisePropertiesFields {
    const val FIELD_AFFISE_APP_ID = "affiseAppId"
    const val FIELD_PART_PARAM_NAME = "partParamName"
    const val FIELD_PART_PARAM_NAME_TOKEN = "partParamNameToken"
    const val FIELD_APP_TOKEN = "appToken"
    const val FIELD_SECRET_ID = "secretId"
    const val FIELD_AUTO_CATCHING_CLICK_EVENTS = "autoCatchingClickEvents"
    const val FIELD_IS_PRODUCTION = "isProduction"
    const val FIELD_ENABLED_METRICS = "enabledMetrics"
}

internal fun Map<*, *>.toAffiseInitProperties(): AffiseInitProperties {
    return AffiseInitProperties(
        this[AffisePropertiesFields.FIELD_AFFISE_APP_ID]?.toString(),
        (this[AffisePropertiesFields.FIELD_IS_PRODUCTION] as? Boolean) ?: false,
        this[AffisePropertiesFields.FIELD_PART_PARAM_NAME]?.toString(),
        this[AffisePropertiesFields.FIELD_PART_PARAM_NAME_TOKEN]?.toString(),
        this[AffisePropertiesFields.FIELD_APP_TOKEN]?.toString(),
        this[AffisePropertiesFields.FIELD_SECRET_ID]?.toString(),
        (this[AffisePropertiesFields.FIELD_AUTO_CATCHING_CLICK_EVENTS] as? List<*>)?.mapNotNull {
            it?.toString()?.toAutoCatchingType()
        },
        this[AffisePropertiesFields.FIELD_ENABLED_METRICS]?.toString()?.toBoolean() ?: false,
    )
}