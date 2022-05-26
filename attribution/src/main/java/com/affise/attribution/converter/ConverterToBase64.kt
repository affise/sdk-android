package com.affise.attribution.converter

import android.util.Base64

/**
 *  Convert string to Base64 [encoded string]
 */
class ConverterToBase64 : Converter<String, String> {

    /**
     * Convert [from] String to encode string with Base64
     */
    override fun convert(
        from: String
    ): String = Base64.encodeToString(from.toByteArray(), Base64.NO_WRAP)
}