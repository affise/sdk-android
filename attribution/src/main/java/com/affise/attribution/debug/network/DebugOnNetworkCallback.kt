package com.affise.attribution.debug.network

import com.affise.attribution.network.HttpRequest
import com.affise.attribution.network.HttpResponse

fun interface DebugOnNetworkCallback {

    fun handle(request: HttpRequest, response: HttpResponse)
}