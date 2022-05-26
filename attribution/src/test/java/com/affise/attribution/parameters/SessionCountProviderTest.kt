package com.affise.attribution.parameters

import com.affise.attribution.session.SessionManager
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

class SessionCountProviderTest {

    @Test
    fun provideDefault() {
        val sessionCount = 0L
        val defaultCount = 1

        val useCase: SessionManager = mockk {
            every {
                getSessionCount()
            } returns sessionCount
        }

        val provider = AffiseSessionCountProvider(useCase)
        val result = provider.provide()

        Truth.assertThat(result).isEqualTo(defaultCount)

        verifyAll {
            useCase.getSessionCount()
        }

    }

    @Test
    fun provide() {
        val sessionCount = 10L

        val useCase: SessionManager = mockk {
            every {
                getSessionCount()
            } returns sessionCount
        }

        val provider = AffiseSessionCountProvider(useCase)
        val result = provider.provide()

        Truth.assertThat(result).isEqualTo(sessionCount)

        verifyAll {
            useCase.getSessionCount()
        }

    }

}