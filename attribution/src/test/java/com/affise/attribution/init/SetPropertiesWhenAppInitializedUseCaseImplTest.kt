package com.affise.attribution.init

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test

/**
 * Test for [SetPropertiesWhenAppInitializedUseCaseImpl]
 */
class SetPropertiesWhenAppInitializedUseCaseImplTest {

    @Test
    fun init() {
        val model: AffiseInitProperties = mockk()
        val storage: InitPropertiesStorage = mockk {
            every {
                setProperties(model)
            } just Runs
        }
        val useCase = SetPropertiesWhenAppInitializedUseCaseImpl(storage)
        useCase.init(model)

        verifyAll {
            storage.setProperties(model)
        }

    }
}