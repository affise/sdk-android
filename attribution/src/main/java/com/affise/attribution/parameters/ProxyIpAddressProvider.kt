package com.affise.attribution.parameters

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.PROXY_IP_ADDRESS]
 *
 * @property context to retrieve ConnectivityManager
 * @property buildConfigPropertiesProvider to get current SDK_INT
 */
class ProxyIpAddressProvider(
    private val context: Context,
    private val buildConfigPropertiesProvider: BuildConfigPropertiesProvider
) : StringPropertyProvider() {

    @SuppressLint("NewApi")
    override fun provide(
    ) = if (buildConfigPropertiesProvider.getSDKVersion() >= Build.VERSION_CODES.M) {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        connectivityManager?.defaultProxy?.host
    } else {
        null
    }
}