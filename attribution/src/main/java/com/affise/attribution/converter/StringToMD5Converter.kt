package com.affise.attribution.converter

import com.affise.attribution.logs.LogsManager
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Converter String to Md5 String
 *
 * @property logsManager for error logging
 */
class StringToMD5Converter(
    private val logsManager: LogsManager
) : Converter<String, String> {

    /**
     * Convert [from] to Md5
     *
     * @return value of md5
     */
    override fun convert(from: String) = generateMd5(from)

    /**
     * Generate md5 from [value]
     *
     * @return value of md5
     */
    private fun generateMd5(value: String): String {
        //Get digits
        val digits = try {
            with(MessageDigest.getInstance(ALGORITHM_NAME)) {
                reset()
                update(value.toByteArray())
                digest()
            }
        } catch (e: NoSuchAlgorithmException) {
            //Log error
            logsManager.addSdkError(e)

            byteArrayOf()
        }

        //Convert digits to BigInteger
        val bigInt = BigInteger(1, digits)

        //Create stringBuilder
        val stringBuilder = StringBuilder(bigInt.toString(16))

        //Filling stringBuilder
        while (stringBuilder.length < 32) {
            stringBuilder.insert(0, "0")
        }

        return stringBuilder.toString()
    }

    companion object {
        private const val ALGORITHM_NAME = "MD5"
    }
}