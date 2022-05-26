package com.affise.attribution.parameters

import android.content.Context
import android.telephony.TelephonyManager
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.ISP
 *
 * @property app to retrieve system service
 */
class IspNameProvider(
    private val app: Context
) : StringPropertyProvider() {

    override fun provide(): String? = (app.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager)
        ?.simOperatorName
        ?.ifEmpty { null }
}