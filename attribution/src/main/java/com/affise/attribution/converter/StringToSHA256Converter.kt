package com.affise.attribution.converter

import java.security.MessageDigest

/**
 * Converter String to Sha256 String
 */
class StringToSHA256Converter : Converter<String, String> {

    /**
     * Convert [from] to Sha256
     *
     * @return value of Sha256
     */
    override fun convert(from: String) = generateSha256(from)

    /**
     * Generate sha1 from [value]
     *
     * @return value of Sha256
     */
    private fun generateSha256(value: String) = MessageDigest.getInstance(ALGORITHM_NAME)
        .digest(value.toByteArray())
        .joinToString("") {
            "%02x".format(it)
        }

    companion object {
        private const val ALGORITHM_NAME = "SHA-256"
    }
}