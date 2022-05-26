package com.affise.attribution.deeplink

import android.net.Uri
import com.affise.attribution.converter.Converter

/**
 * Implementation of [Converter]
 *
 * Converts uri string to [Uri], extracting deeplink from uri parameter
 */
class InstallReferrerToDeeplinkUriConverter : Converter<String, Uri?> {
    override fun convert(from: String): Uri? {
        val uri = Uri.parse(from)
        return extractDeeplinkFromAbsolute(uri) ?: extractDeeplinkFromRelative(from)
    }

    private fun extractDeeplinkFromRelative(from: String): Uri? =
        try {
            Uri.Builder()
                .encodedQuery(from)
                .build()
                .let(::extractDeeplinkFromAbsolute)
        } catch (e: Exception) {
            null
        }

    private fun extractDeeplinkFromAbsolute(from: Uri): Uri? =
        try {
            from.getQueryParameter(DEEPLINK_PARAM_NAME)
                ?.let(Uri::parse)
        } catch (e: Exception) {
            null
        }

    private companion object {
        const val DEEPLINK_PARAM_NAME = "deeplink"
    }
}