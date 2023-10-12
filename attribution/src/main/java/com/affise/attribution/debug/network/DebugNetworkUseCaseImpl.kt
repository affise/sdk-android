package com.affise.attribution.debug.network

import com.affise.attribution.init.AffiseInitProperties
import com.affise.attribution.network.HttpClient

class DebugNetworkUseCaseImpl(
    private val initProperties: AffiseInitProperties,
    private val httpClient: HttpClient
) : DebugNetworkUseCase {

    override fun onRequest(onDebug: DebugOnNetworkCallback) {
        if (initProperties.isProduction) return
        httpClient.debugRequest = onDebug
    }
}