package com.affise.attribution.parameters

import com.affise.attribution.BuildConfig
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.AFFISE_SDK_VERSION]
 */
class AffSDKVersionProvider : StringPropertyProvider() {

    override val order: Float = 47.0f
    override val key: String = Parameters.AFFISE_SDK_VERSION

    override fun provide(): String = BuildConfig.VERSION_NAME
}