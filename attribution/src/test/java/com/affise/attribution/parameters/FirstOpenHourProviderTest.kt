package com.affise.attribution.parameters

import com.affise.attribution.usecase.FirstAppOpenUseCase
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test
import java.util.Date

/**
 * Test for [FirstOpenHourProvider]
 */
class FirstOpenHourProviderTest {

    @Test
    fun `verify when usecase returns valid date it is stripped`() {
        val date = Date(1635176812345)
        val useCase: FirstAppOpenUseCase = mockk {
            every {
                getFirstOpenDate()
            } returns date
        }
        val provider = FirstOpenHourProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(1635174000000)
        verifyAll {
            useCase.getFirstOpenDate()
        }
    }

    @Test
    fun `verify when usecase returns null result is null`() {
        val useCase: FirstAppOpenUseCase = mockk {
            every {
                getFirstOpenDate()
            } returns null
        }
        val provider = FirstOpenHourProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            useCase.getFirstOpenDate()
        }
    }

    @Test
    fun `verify when usecase returns invalid date result is null`() {
        val date = Date(0)
        val useCase: FirstAppOpenUseCase = mockk {
            every {
                getFirstOpenDate()
            } returns date
        }
        val provider = FirstOpenHourProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            useCase.getFirstOpenDate()
        }
    }
}