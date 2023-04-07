package com.affise.attribution.module.advertising.oaid.hms

import android.content.Context
import android.content.Intent
import java.lang.Exception

/**
 * Client to get OAID information
 *
 * @property context to bind or unbind service.
 * This defines a dependency between your application and the service.
 * @property maxWaitTime the max connection time to the service.
 * @property connector for monitoring the state of an application service.
 */
class OpenDeviceIdentifierClient(
    private val context: Context,
    private val maxWaitTime: Long,
    private val connector: OpenDeviceIdentifierConnector
) {

    /**
     * Get Oaid
     */
    fun getOaidInfo(): String? = getServiceConnector(context)
        ?.let { serviceConnector ->
            //Get serviceConnector
            serviceConnector.getOpenDeviceIdentifierService(maxWaitTime)
                //Get Oaid in serviceConnector
                ?.getOaid()
                ?: run {
                    //Unbind
                    serviceConnector.unbindAndReset()
                    null
                }
        }

    /**
     * Get serviceConnector with [context]
     */
    private fun getServiceConnector(context: Context): OpenDeviceIdentifierConnector? {
        //Check if service connected
        if (connector.isServiceConnected) {
            //If connected return connector
            return connector
        }

        //Create service
        val intentForOaidService = Intent(OAID_INTENT_ACTION)

        //Set package to service
        intentForOaidService.setPackage(HUAWEI_PACKAGE_NAME)

        //Create couldBind flag
        var couldBind = false

        return try {
            //Unbind
            connector.startBindings()

            //Bind and chang couldBind flag
            couldBind = context.bindService(
                intentForOaidService,
                connector,
                Context.BIND_AUTO_CREATE
            )

            //Check couldBind flag and return connector if service binding
            if (couldBind) connector else null
        } catch (e: Exception) {
            null
        } finally {
            //If service binding unbind
            if (!couldBind) {
                connector.unbindAndReset()
            }
        }
    }

    companion object {
        private const val OAID_INTENT_ACTION = "com.uodis.opendevice.OPENIDS_SERVICE"
        private const val HUAWEI_PACKAGE_NAME = "com.huawei.hwid"
    }
}