package com.affise.attribution.debug.network

internal interface DebugNetworkUseCase {

    fun onRequest(onComplete: DebugOnNetworkCallback)
}