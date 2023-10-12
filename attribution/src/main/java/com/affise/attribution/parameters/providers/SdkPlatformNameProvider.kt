package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.internal.platform.InternalSdkPlatform
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [ProviderType.SDK_PLATFORM]
 */
class SdkPlatformNameProvider : StringPropertyProvider() {

    override val order: Float = 45.0f
    override val key: ProviderType = ProviderType.SDK_PLATFORM

    override fun provide(): String = InternalSdkPlatform.info
}