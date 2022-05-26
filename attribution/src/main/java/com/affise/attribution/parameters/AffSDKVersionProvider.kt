package com.affise.attribution.parameters

import com.affise.attribution.BuildConfig
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.AFFISE_SDK_VERSION]
 */
class AffSDKVersionProvider : StringPropertyProvider() {

    override fun provide(): String = BuildConfig.VERSION_NAME
}