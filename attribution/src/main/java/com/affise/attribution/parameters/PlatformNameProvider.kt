package com.affise.attribution.parameters

import com.affise.attribution.platform.SdkPlatform
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.PLATFORM]
 */
class PlatformNameProvider : StringPropertyProvider() {

    override fun provide(): String = SdkPlatform.ANDROID
}