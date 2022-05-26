package com.affise.attribution.parameters

import com.affise.attribution.usecase.FirstAppOpenUseCase
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Assert.*

import org.junit.Test
import java.util.Date

/**
 * Test for [InstallFinishTimeProvider]
 */
class InstallFinishTimeProviderTest {

    @Test
    fun `verify when usecase returns valid date`() {
        val date = Date(1635176812345)
        val useCase: FirstAppOpenUseCase = mockk {
            every {
                getFirstOpenDate()
            } returns date
        }
        val provider = InstallFinishTimeProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(1635176812345)
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
        val provider = InstallFinishTimeProvider(useCase)
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
        val provider = InstallFinishTimeProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(null)
        verifyAll {
            useCase.getFirstOpenDate()
        }
    }
}