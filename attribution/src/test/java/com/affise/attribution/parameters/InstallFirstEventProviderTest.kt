package com.affise.attribution.parameters

import com.affise.attribution.usecase.FirstAppOpenUseCase
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test


/**
 * Test for [InstallFirstEventProvider]
 */
class InstallFirstEventProviderTest {

    @Test
    fun `verify when usecase returns valid true`() {
        val isFirstOpen = true
        val useCase: FirstAppOpenUseCase = mockk {
            every {
                isFirstOpen()
            } returns isFirstOpen
        }
        val provider = InstallFirstEventProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(isFirstOpen)
        verifyAll {
            useCase.isFirstOpen()
        }
    }

    @Test
    fun `verify when usecase returns valid false`() {
        val isFirstOpen = false
        val useCase: FirstAppOpenUseCase = mockk {
            every {
                isFirstOpen()
            } returns isFirstOpen
        }
        val provider = InstallFirstEventProvider(useCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo(isFirstOpen)
        verifyAll {
            useCase.isFirstOpen()
        }
    }
}