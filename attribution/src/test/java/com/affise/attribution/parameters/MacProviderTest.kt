package com.affise.attribution.parameters

import com.affise.attribution.logs.LogsManager
import com.google.common.truth.Truth
import io.mockk.Called
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [MacProviderTest]
 */
class MacProviderTest {

    @Test
    fun `verify provide returns null`() {
        val logsManager: LogsManager = mockk()

        val provider = MacProvider(logsManager)
        Truth.assertThat(provider.provide()).isNull()

        verifyAll {
            logsManager wasNot Called
        }

    }

}