package com.affise.attribution.parameters

import com.affise.attribution.oaid.OaidManager
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.OAID]
 *
 * @property oaidManager to retrieve oaid
 */
class OaidProvider(
    private val oaidManager: OaidManager
) : StringPropertyProvider() {

    override fun provide(): String? = oaidManager.getOaid()
}