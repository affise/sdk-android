package com.affise.attribution.advertising

import android.app.Application

/**
 * Manager to track google advertising id changes
 */
interface AdvertisingIdManager {
    /**
     * Called on [app] init
     */
    fun init(app: Application)

    /**
     * Returns google advertising id if present
     */
    fun getAdvertisingId(): String?
}