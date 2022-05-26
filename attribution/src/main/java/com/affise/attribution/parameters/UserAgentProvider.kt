package com.affise.attribution.parameters

import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.USER_AGENT]
 */
class UserAgentProvider : StringPropertyProvider() {

    override fun provide(): String? = System.getProperty("http.agent")
}