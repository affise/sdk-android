package com.affise.attribution.parameters

import android.content.Context
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.AFFISE_PKG_APP_NAME]
 *
 * @property context to retrieve package name from
 */
class AffisePackageAppNameProvider(
    private val context: Context
) : StringPropertyProvider() {

    override val order: Float = 2.0f
    override val key: String = Parameters.AFFISE_PKG_APP_NAME

    override fun provide(): String? = context.packageName
}