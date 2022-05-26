package com.affise.attribution.parameters

import com.google.common.truth.Truth
import io.mockk.*
import org.junit.Test
import java.util.Calendar

class CreatedTimeMilliProviderTest {

    @Test
    fun provide() {
        val testTimeInSec = 1403568849259
        val testTimeInMillis = 1403568849259L

        val calendar: Calendar = mockk {
            every {
                timeInMillis
            } returns testTimeInMillis
        }

        mockkStatic(Calendar::class) {
            every {
                Calendar.getInstance()
            } returns calendar

            val provider = CreatedTimeMilliProvider()
            val result = provider.provide()

            Truth.assertThat(testTimeInSec).isEqualTo(result)
        }

        verifyAll {
            Calendar.getInstance()
            calendar.timeInMillis
        }

    }
}