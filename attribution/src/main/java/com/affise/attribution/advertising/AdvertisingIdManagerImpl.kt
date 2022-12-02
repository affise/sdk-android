package com.affise.attribution.advertising

import android.app.Application
import android.content.Context
import android.content.Intent
import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.logs.LogsManager


/**
 * Manager to track google advertising id changes
 *
 * @property connection Receives information as the service is started and stopped.
 * @property executorProvider An Executor that provides methods to manage termination and methods
 * that can produce a Future for tracking progress of one or more asynchronous tasks.
 * @property logsManager for save errors
 */
internal class AdvertisingIdManagerImpl(
    private val connection: GoogleIdentifierConnection,
    private val executorProvider: ExecutorServiceProvider,
    private val logsManager: LogsManager
) : AdvertisingIdManager {

    /**
     * Google Advertising ID
     */
    private var advertisingId: String? = null

    /**
     * Init Advertising IdManager
     */
    override fun init(app: Application) = executorProvider.provideExecutorService().execute {
        try {
            //Get Google Advertising ID with context
            advertisingId = getGoogleAdvertisingId(app)
        } catch (throwable: Throwable) {
            //Log error
            logsManager.addDeviceError(throwable)
        }
    }

    /**
     * Get cashed Google Advertising ID
     * @return Google Advertising ID
     */
    override fun getAdvertisingId() = advertisingId

    /**
     * Get Google Advertising ID with [context] app
     */
    private fun getGoogleAdvertisingId(context: Context): String? {
        try {
            //Check Package info of com.android.vending
            context.packageManager.getPackageInfo(PACKAGE_VENDING, 0)
        } catch (throwable: Throwable) {
            //Log error
            logsManager.addDeviceError(throwable)
            return null
        }
        //Create Intent
        val intent = Intent()

        //Add action to Intent
        intent.action = INTENT_IDENTIFIER_SERVICE_START

        //Add package to Intent
        intent.setPackage(PACKAGE_GOOGLE_GMS)

        try {
            //Check binding
            if (context.bindService(intent, connection, Context.BIND_AUTO_CREATE)) {
                connection.getBinder()?.let {
                    //Create GoogleIdentifierInterface and get Google Advertising ID
                    return GoogleIdentifierInterface(it).getGoogleAdid()
                }
            }
        } catch (throwable: Throwable) {
            //Log error
            logsManager.addDeviceError(throwable)
        } finally {
            //Unbind
            context.unbindService(connection)
        }

        return null
    }

    companion object {
        private const val PACKAGE_VENDING = "com.android.vending"
        private const val PACKAGE_GOOGLE_GMS = "com.google.android.gms"
        private const val INTENT_IDENTIFIER_SERVICE_START = "com.google.android.gms.ads.identifier.service.START"
    }
}