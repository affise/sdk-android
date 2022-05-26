package com.affise.attribution.network

import java.net.URL

interface HttpClient {

    /**
     * Method of connection
     */
    enum class Method {
        GET,
        POST
    }

    /**
     * Create request and execute result
     * Executes [method] on url [httpsUrl] with body of [data] and [headers]
     *
     * @return string representation of request
     */
    fun executeRequest(
        httpsUrl: URL,
        method: Method,
        data: String,
        headers: Map<String, String>
    ): String?
}