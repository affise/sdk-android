package com.affise.attribution.converter

import java.security.MessageDigest

/**
 * Converter String to Sha1 String
 */
class StringToSHA1Converter : Converter<String, String> {

    /**
     * Convert [from] to Sha1
     *
     * @return value of Sha1
     */
    override fun convert(from: String) = generateSha1(from)

    /**
     * Generate sha1 from [value]
     *
     * @return value of Sha1
     */
    private fun generateSha1(value: String) = MessageDigest.getInstance(ALGORITHM_NAME)
        .digest(value.toByteArray())
        .joinToString("") {
            "%02x".format(it)
        }

    companion object {
        private const val ALGORITHM_NAME = "SHA-1"
    }
}