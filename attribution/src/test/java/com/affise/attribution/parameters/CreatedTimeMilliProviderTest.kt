package com.affise.attribution.parameters

import com.affise.attribution.utils.timestamp
import com.google.common.truth.Truth
import io.mockk.*
import org.junit.Test

class CreatedTimeMilliProviderTest {

    @Test
    fun provide() {
        val testTimeInSec = 1403568849259
        val testTimeInMillis = 1403568849259L

        mockkStatic(::timestamp) {
            every {
                timestamp()
            } returns testTimeInMillis

            val provider = CreatedTimeMilliProvider()
            val result = provider.provide()

            Truth.assertThat(testTimeInSec).isEqualTo(result)
        }
    }
}
