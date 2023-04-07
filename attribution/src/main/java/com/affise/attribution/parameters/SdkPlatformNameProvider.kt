package com.affise.attribution.parameters

import com.affise.attribution.platform.SdkPlatform
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.SDK_PLATFORM]
 */
class SdkPlatformNameProvider : StringPropertyProvider() {

    override val order: Float = 45.0f
    override val key: String = Parameters.SDK_PLATFORM

    override fun provide(): String = SdkPlatform.info
}