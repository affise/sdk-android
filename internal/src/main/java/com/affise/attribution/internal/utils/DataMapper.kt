package com.affise.attribution.internal.utils

import com.affise.attribution.deeplink.DeeplinkValue
import com.affise.attribution.network.HttpRequest
import com.affise.attribution.network.HttpResponse

object DataMapper {
    fun fromDeeplinkValue(data: DeeplinkValue): Map<String, *> = mapOf(
        "deeplink" to data.deeplink,
        "scheme" to data.scheme,
        "host" to data.host,
        "path" to data.path,
        "parameters" to data.parameters
    )

    fun fromRequest(data: HttpRequest): Map<String, *> = mapOf(
        "method" to data.method.toString(),
        "url" to data.url.toString(),
        "headers" to data.headers,
        "body" to data.body,
    )

    fun fromResponse(data: HttpResponse): Map<String, *> = mapOf(
        "code" to data.code,
        "message" to data.message,
        "body" to data.body,
        "headers" to data.headers,
    )
}