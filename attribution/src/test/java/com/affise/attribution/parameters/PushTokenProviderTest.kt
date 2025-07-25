package com.affise.attribution.parameters

import com.affise.attribution.parameters.providers.PushTokenProvider
import com.affise.attribution.usecase.PushTokenUseCase
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

class PushTokenProviderTest {

    @Test
    fun provideNull() {
        val useCase: PushTokenUseCase = mockk {
            every {
                getPushToken()
            } returns null
        }

        val provider = PushTokenProvider(useCase)
        val result = provider.provide()

        Truth.assertThat(result).isNull()

        verifyAll {
            useCase.getPushToken()
        }
    }

    @Test
    fun provide() {
        val pushtoken = "pushtoken"

        val useCase: PushTokenUseCase = mockk {
            every {
                getPushToken()
            } returns pushtoken
        }

        val provider = PushTokenProvider(useCase)
        val result = provider.provide()

        Truth.assertThat(result).isEqualTo(pushtoken)

        verifyAll {
            useCase.getPushToken()
        }
    }
}