package com.affise.attribution.module.advertising.advertising

import android.app.Application
import android.content.Context
import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.logs.LogsManager
import com.google.android.gms.ads.identifier.AdvertisingIdClient


/**
 * Manager to track google advertising id changes
 *
 * @property executorProvider An Executor that provides methods to manage termination and methods
 * that can produce a Future for tracking progress of one or more asynchronous tasks.
 * @property logsManager for save errors
 */
internal class AdvertisingIdManagerImpl(
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
        return try {
            //Get Google Advertising ID
            AdvertisingIdClient.getAdvertisingIdInfo(context).id
        } catch (throwable: Throwable) {
            null
        }
    }
}