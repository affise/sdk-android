package com.affise.attribution.network

object CloudConfig {

    /**
     * Urls for send data
     */
    private val urls: List<String> = listOf(
        "https://tracking.affattr.com/postback"
    )

    val headers: Map<String, String> = mapOf(
        "Content-Type" to "application/json; charset=utf-8"
    )

    fun getUrls() = urls
}