package com.affise.attribution.parameters

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verifyAll
import io.mockk.verifySequence
import org.junit.Test
import java.util.Calendar
import java.util.TimeZone

/**
 * Test for [TimezoneDeviceProvider]
 */
class TimezoneDeviceProviderTest {

    @Test
    fun provide() {
        mockkStatic(Calendar::class) {
            val date = 1609448400L
            val timeZoneMock: TimeZone = mockk {
                every {
                    getOffset(date)
                } returns 19800000
            }
            val calendar: Calendar = mockk {
                every {
                    timeZone
                } returns timeZoneMock

                every {
                    timeInMillis
                } returns date
            }

            every {
                Calendar.getInstance()
            } returns calendar

            val provider = TimezoneDeviceProvider()

            val actual = provider.provide()
            Truth.assertThat(actual).isEqualTo("UTC+0530")

            verifyAll {
                Calendar.getInstance()
                calendar.timeZone
                calendar.timeInMillis
                timeZoneMock.getOffset(date)
            }

        }
    }
}