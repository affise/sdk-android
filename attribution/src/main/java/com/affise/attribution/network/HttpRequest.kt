package com.affise.attribution.network

import java.net.URL

data class HttpRequest(
    val url: URL,
    val method: HttpClient.Method,
    val headers: Map<String, String>,
    val body: String?,
)
