package com.affise.attribution.parameters.providers

import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [ProviderType.USER_AGENT]
 */
class UserAgentProvider : StringPropertyProvider() {

    override val order: Float = 35.0f
    override val key: ProviderType = ProviderType.USER_AGENT

    override fun provide(): String? = System.getProperty("http.agent")
}