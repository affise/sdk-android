package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.USER_AGENT]
 */
class UserAgentProvider : StringPropertyProvider() {

    override val order: Float = 35.0f
    override val key: String = Parameters.USER_AGENT

    override fun provide(): String? = System.getProperty("http.agent")
}