package com.affise.attribution.network

object CloudConfig {

    /**
     * Urls for send data
     */
    private val urls: List<String> = listOf(
        "https://tracking.affattr.com/postback"
    )

    fun getUrls() = urls
}