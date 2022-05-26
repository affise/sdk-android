package com.affise.attribution.converter

import com.affise.attribution.logs.LogsManager
import com.google.common.truth.Truth
import io.mockk.*
import org.junit.Test
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Test for [StringToMD5Converter]
 */
class StringToMD5ConverterTest {

    @Test
    fun `verify convert returns valid md5 string is message digest is present`() {
        val logsManager: LogsManager = mockk()
        val converter = StringToMD5Converter(logsManager)

        val actual = converter.convert(STRING_RAW)
        Truth.assertThat(actual).isEqualTo(STRING_MD5)

    }

    @Test
    fun `verify convert returns empty string is message digest is thows exception`() {
        val exeption = NoSuchAlgorithmException()

        val logsManager: LogsManager = mockk {
            every {
                addSdkError(exeption)
            } just Runs
        }

        mockkStatic(MessageDigest::class) {
            every {
                MessageDigest.getInstance(ALGORITHM_NAME)
            } throws exeption

            val converter = StringToMD5Converter(logsManager)

            val actual = converter.convert(STRING_RAW)
            Truth.assertThat(actual).isEqualTo(STRING_MD5_UNKNOWN_DIGEST)

            verifyAll {
                MessageDigest.getInstance(ALGORITHM_NAME)
                logsManager.addSdkError(exeption)
            }
        }
    }

    companion object {
        private const val ALGORITHM_NAME = "MD5"
        private const val STRING_RAW = "raw string to encode"
        private const val STRING_MD5 = "950e61ca1a9e8f6d08de8234ac45bbd0"
        private const val STRING_MD5_UNKNOWN_DIGEST = "00000000000000000000000000000000"
    }

}