package com.affise.attribution.init

import com.affise.attribution.events.autoCatchingClick.AutoCatchingType

/**
 * Model that holds properties required on library init
 */
data class AffiseInitProperties(
    val affiseAppId: String?,
    val isProduction: Boolean = true,
    val partParamName: String? = null,
    val partParamNameToken: String? = null,
    val appToken: String? = null,
    val secretKey: String? = null,
    val autoCatchingClickEvents: List<AutoCatchingType>? = null,
    val enabledMetrics: Boolean = false,
) {
    constructor(
        affiseAppId: String?,
        secretKey: String? = null
    ) : this(
        affiseAppId = affiseAppId,
        isProduction = true,
        partParamName = null,
        partParamNameToken = null,
        appToken = null,
        secretKey = secretKey,
        autoCatchingClickEvents = null,
        enabledMetrics = false
    )
}
