package com.affise.attribution.debug

import com.affise.attribution.debug.network.DebugOnNetworkCallback
import com.affise.attribution.debug.validate.DebugOnValidateCallback

interface AffiseDebugApi {
    /**
     * Won't work on Production
     *
     * Validate credentials
     */
    fun validate(callback: DebugOnValidateCallback)

    /**
     * Won't work on Production
     *
     * Show request/response data
     */
    fun network(callback: DebugOnNetworkCallback)

    /**
     * Show version
     */
    fun version(): String
}