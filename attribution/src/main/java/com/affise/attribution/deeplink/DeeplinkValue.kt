package com.affise.attribution.deeplink

import android.net.Uri

data class DeeplinkValue(
    val deeplink: String,
    val scheme: String?,
    val host: String?,
    val path: String?,
    val parameters: Map<String, List<String>>
)


fun Uri.toDeeplinkValue() = DeeplinkValue(
    deeplink = this.toString(),
    scheme = this.scheme,
    host = this.host,
    path = this.path,
    parameters = this.queryParameterNames.associateWith { this.getQueryParameters(it) }
)