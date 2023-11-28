package com.affise.attribution.network

import android.net.Uri

object CloudConfig {

    internal const val defaultDomain: String = "https://tracking.affattr.com/"
    private const val path: String = "postback"

    private var domain: String = defaultDomain

    /**
     * Urls for send data
     */
    private var urls: List<String> = listOf(
        "${domain}${path}"
    )

    val headers: Map<String, String> = mapOf(
        "Content-Type" to "application/json; charset=utf-8"
    )

    fun getUrls() = urls

    internal fun setupDomain(domain: String?) {
        if (domain.isNullOrBlank()) return

        this.domain = if (domain.endsWith("/")) domain else "$domain/"

        urls = listOf(
            getURL(path)
        )
    }

    fun getURL(path: String): String = try {
        Uri.parse(domain)
            .buildUpon()
            .appendEncodedPath(path)
            .build().toString()
    } catch (_: Exception) {
        "$defaultDomain$path"
    }
}