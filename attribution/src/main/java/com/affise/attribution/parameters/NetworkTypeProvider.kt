package com.affise.attribution.parameters

import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.TelephonyManager
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.NETWORK_TYPE]
 *
 * @property app manager to fetch resources from
 */
class NetworkTypeProvider(
    private val app: Application
) : StringPropertyProvider() {

    override fun provide(): String? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        app.getSystemService(ConnectivityManager::class.java)
    } else {
        app.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    }?.let { connectivityManager ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getTypeAboveMarshmallow(connectivityManager)
        } else {
            getTypeBelowMarshmallow(connectivityManager)
        }
    }

    @Suppress("DEPRECATION")
    private fun getTypeBelowMarshmallow(
        connectivityManager: ConnectivityManager
    ): String? = connectivityManager.activeNetworkInfo
        ?.takeIf { it.isConnected }
        ?.let {
            when (it.type) {
                ConnectivityManager.TYPE_WIFI -> "WIFI"
                ConnectivityManager.TYPE_MOBILE -> {
                    getNetworkType(it.subtype)
                }
                else -> null
            }
        }

    @TargetApi(Build.VERSION_CODES.M)
    private fun getTypeAboveMarshmallow(
        connectivityManager: ConnectivityManager
    ): String? = connectivityManager.activeNetwork
        ?.let { connectivityManager.getNetworkCapabilities(it) }
        ?.let { networkCapabilities ->
            when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WIFI"
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    (app.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager)
                        ?.let { telephonyManager ->
                            val permissionStatus = app.checkSelfPermission(
                                android.Manifest.permission.READ_PHONE_STATE
                            )

                            if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    getNetworkType(telephonyManager.dataNetworkType)
                                } else {
                                    @Suppress("DEPRECATION")
                                    getNetworkType(telephonyManager.networkType)
                                }
                            } else {
                                getTypeBelowMarshmallow(connectivityManager)
                            }
                        }
                }
                else -> null
            }
        }

    private fun getNetworkType(type: Int) = when (type) {
        TelephonyManager.NETWORK_TYPE_UNKNOWN -> ""
        TelephonyManager.NETWORK_TYPE_GPRS -> "GPRS"
        TelephonyManager.NETWORK_TYPE_EDGE -> "EDGE"
        TelephonyManager.NETWORK_TYPE_UMTS -> "UMTS"
        TelephonyManager.NETWORK_TYPE_CDMA -> "CDMA"
        TelephonyManager.NETWORK_TYPE_EVDO_0 -> "EVDO_0"
        TelephonyManager.NETWORK_TYPE_EVDO_A -> "EVDO_A"
        TelephonyManager.NETWORK_TYPE_HSDPA -> "HSDPA"
        TelephonyManager.NETWORK_TYPE_HSUPA -> "HSUPA"
        TelephonyManager.NETWORK_TYPE_HSPA -> "HSPA"
        TelephonyManager.NETWORK_TYPE_IDEN -> "IDEN"
        TelephonyManager.NETWORK_TYPE_EVDO_B -> "EVDO_B"
        TelephonyManager.NETWORK_TYPE_LTE -> "LTE"
        TelephonyManager.NETWORK_TYPE_EHRPD -> "EHRPD"
        TelephonyManager.NETWORK_TYPE_HSPAP -> "HSPAP"
        TelephonyManager.NETWORK_TYPE_GSM -> "GSM"
        TelephonyManager.NETWORK_TYPE_TD_SCDMA -> "TD_SCDMA"
        TelephonyManager.NETWORK_TYPE_IWLAN -> "IWLAN"
        TelephonyManager.NETWORK_TYPE_NR -> "NR"
        else -> null
    }
}