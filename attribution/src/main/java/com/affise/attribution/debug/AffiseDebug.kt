package com.affise.attribution.debug

import com.affise.attribution.Affise
import com.affise.attribution.AffiseApi
import com.affise.attribution.BuildConfig
import com.affise.attribution.debug.network.DebugOnNetworkCallback
import com.affise.attribution.debug.validate.DebugOnValidateCallback

internal class AffiseDebug: AffiseDebugApi {

    val api: AffiseApi?
        get() = Affise.api
    /**
     * Won't work on Production
     *
     * Validate credentials
     */
    override fun validate(callback: DebugOnValidateCallback) {
        api?.debugValidateUseCase?.validate(callback)
    }

    /**
     * Won't work on Production
     *
     * Show request/response data
     */
    override fun network(callback: DebugOnNetworkCallback) {
        api?.debugNetworkUseCase?.onRequest(callback)
    }

    /**
     * Show version
     */
    override fun version(): String {
        return BuildConfig.AFFISE_VERSION
    }
}