package com.affise.attribution.events

import com.affise.attribution.network.HttpResponse

fun interface OnSendFailedCallback {
    fun handle(status: HttpResponse): Boolean
}