package com.affise.attribution.parameters

import com.google.common.truth.Truth
import io.mockk.*
import org.junit.Test
import java.util.Calendar

class CreatedTimeHourProviderTest {

    @Test
    fun provide() {
        val testTimeInSec = 1636722000000
        val testTimeHour = 1636722000000

        val calendar: Calendar = mockk {
            every {
                set(Calendar.MILLISECOND, 0)
            } returns Unit

            every {
                set(Calendar.SECOND, 0)
            } returns Unit

            every {
                set(Calendar.MINUTE, 0)
            } returns Unit

            every {
                timeInMillis
            } returns testTimeHour
        }

        mockkStatic(Calendar::class) {
            every {
                Calendar.getInstance()
            } returns calendar

            val provider = CreatedTimeHourProvider()
            val result = provider.provide()

            Truth.assertThat(testTimeInSec).isEqualTo(result)
        }

        verifyAll {
            Calendar.getInstance()
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.timeInMillis
        }

    }
}