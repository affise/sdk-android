package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.platform.SdkPlatform
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [ProviderType.PLATFORM]
 */
class PlatformNameProvider : StringPropertyProvider() {

    override val order: Float = 44.0f
    override val key: ProviderType = ProviderType.PLATFORM

    override fun provide(): String = SdkPlatform.ANDROID
}