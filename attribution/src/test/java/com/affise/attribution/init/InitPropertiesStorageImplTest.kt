package com.affise.attribution.init

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [InitPropertiesStorageImpl]
 */
class InitPropertiesStorageImplTest {

    @Test
    fun `verify setProperties stores data in shared preferences`() {
        val affiseAppIdMock = "id"

        val model: AffiseInitProperties = mockk {
            every {
                affiseAppId
            } returns affiseAppIdMock
        }
        val storage = InitPropertiesStorageImpl()
        storage.setProperties(model)

        Truth.assertThat(storage.getProperties()?.affiseAppId).isEqualTo("id")

        verifyAll {
            model.affiseAppId
        }
    }
}