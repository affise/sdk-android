package com.affise.attribution.parameters

import com.affise.attribution.usecase.FirstAppOpenUseCase
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test
import java.util.Date

/**
 * Test for [FirstOpenTimeProvider]
 */
class FirstOpenTimeProviderTest {

    @Test
    fun `verify provide when firstAppOpenUseCase returns date should return date string`() {
        val time = 222L
        val firstOpenDate: Date = mockk {
            every {
                getTime()
            } returns time
        }
        val useCase: FirstAppOpenUseCase = mockk {
            every {
                getFirstOpenDate()
            } returns firstOpenDate
        }
        val provider = FirstOpenTimeProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(time)
        verifyAll {
            useCase.getFirstOpenDate()
            firstOpenDate.time
        }
    }

    @Test
    fun `verify provide when firstAppOpenUseCase null should return null`() {
        val useCase: FirstAppOpenUseCase = mockk {
            every {
                getFirstOpenDate()
            } returns null
        }
        val provider = FirstOpenTimeProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            useCase.getFirstOpenDate()
        }
    }
}