package com.affise.attribution.parameters

import com.affise.attribution.utils.generateUUID
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Test
import java.util.*

/**
 * Test for [UuidProvider]
 */
class UuidProviderTest {

    @Test
    fun provide() {
        mockkStatic(::generateUUID) {
            every {
                generateUUID()
            } returns UUID(0, 0)

            val actualUuid = UuidProvider().provide()
            Truth.assertThat(defaultUuidString).isEqualTo(actualUuid)
        }
    }

    companion object {
        const val defaultUuidString = "00000000-0000-0000-0000-000000000000"
    }

}