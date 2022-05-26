package com.affise.attribution.parameters

import com.google.common.truth.Truth
import io.mockk.*
import org.junit.Test
import java.util.Calendar

class CreatedTimeProviderTest {

    @Test
    fun provide() {
        val testTimeInSec = 1403568849259L
        val testTimeInMillis = 1403568849259L

        val calendar: Calendar = mockk {
            every {
                set(Calendar.MILLISECOND, 0)
            } just Runs

            every {
                timeInMillis
            } returns testTimeInMillis
        }

        mockkStatic(Calendar::class) {
            every {
                Calendar.getInstance()
            } returns calendar

            val provider = CreatedTimeProvider()
            val result = provider.provide()

            Truth.assertThat(testTimeInSec).isEqualTo(result)
        }

        verifyAll {
            Calendar.getInstance()
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.timeInMillis
        }
    }

}