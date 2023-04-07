package com.affise.attribution.parameters

import com.affise.attribution.platform.SdkPlatform
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.PLATFORM]
 */
class PlatformNameProvider : StringPropertyProvider() {

    override val order: Float = 44.0f
    override val key: String = Parameters.PLATFORM

    override fun provide(): String = SdkPlatform.ANDROID
}