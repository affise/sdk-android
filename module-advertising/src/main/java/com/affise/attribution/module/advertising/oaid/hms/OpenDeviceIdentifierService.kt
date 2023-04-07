package com.affise.attribution.module.advertising.oaid.hms

import android.os.IInterface

interface OpenDeviceIdentifierService : IInterface {

    /**
     * Get Oaid
     */
    fun getOaid(): String?
}