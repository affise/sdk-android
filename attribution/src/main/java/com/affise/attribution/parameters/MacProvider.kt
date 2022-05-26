package com.affise.attribution.parameters

import android.annotation.SuppressLint
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.parameters.base.StringPropertyProvider
import java.net.NetworkInterface

/**
 * MAC address provider
 *
 * @property logsManager for error logging
 */
class MacProvider(
    private val logsManager: LogsManager
) : StringPropertyProvider() {

    @SuppressLint("HardwareIds")
    override fun provide(): String? {
        try {
            //Get network interfaces
            val all = NetworkInterface.getNetworkInterfaces()

            //For all interfaces
            for (nif in all) {
                //Check wlan0
                if (!nif.name.equals("wlan0", ignoreCase = true)) continue

                //Get hardware address data
                return nif.hardwareAddress
                    ?.map { String.format("%02X", it) }
                    ?.reduce { acc, value -> "$acc:$value" }
            }
        } catch (ex: Exception) {
            //log error
            logsManager.addDeviceError(ex)
        }

        return null
    }
}