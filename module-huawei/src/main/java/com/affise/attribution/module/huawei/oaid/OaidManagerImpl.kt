package com.affise.attribution.module.huawei.oaid

import android.content.Context
import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.logs.LogsManager
import com.huawei.hms.ads.identifier.AdvertisingIdClient


internal class OaidManagerImpl(
    private val logsManager: LogsManager?,
    private val executorServiceProvider: ExecutorServiceProvider,
) : OaidManager {

    private var advertisingIdInfo: AdvertisingIdClient.Info? = null

    /**
     * is Available Msa Sdk
     */
    private var oaid: String? = null

    override fun init(app: Context?) {
        app ?: return

        executorServiceProvider.provideExecutorService().execute {
            try {
                advertisingIdInfo = getAdvertisingIdInfo(app)
                //Get Open Advertising Identifier (OAID)
                oaid = getOpenAdvertisingIdentifier()
            } catch (throwable: Throwable) {
                //Log error
                logsManager?.addDeviceError(throwable)
            }
        }
    }

    /**
     * Get OAID
     */
    override fun getOaid() = oaid

    private fun getAdvertisingIdInfo(context: Context): AdvertisingIdClient.Info? = try {
        AdvertisingIdClient.getAdvertisingIdInfo(context)
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
//        logsManager?.addDeviceError(throwable)
        null
    }

    /**
     * Get Open Advertising Identifier (OAID)
     */
    private fun getOpenAdvertisingIdentifier(): String? {
        //Get Open Advertising Identifier (OAID)
        return advertisingIdInfo?.id
    }
}