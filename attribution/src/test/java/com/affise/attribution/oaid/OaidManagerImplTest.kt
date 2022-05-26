package com.affise.attribution.oaid

import android.content.Context
import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.oaid.hms.OpenDeviceIdentifierClient
import com.affise.attribution.oaid.hms.OpenDeviceIdentifierConnector
import com.google.common.truth.Truth
import com.google.common.util.concurrent.MoreExecutors
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Test for [OaidManagerImpl]
 */
class OaidManagerImplTest {

    private val logsManager: LogsManager = mockk {
        every { addDeviceError("Library OAID not integration in project") } just Runs
    }

    private val executorServiceProvider: ExecutorServiceProvider = mockk {
        every {
            provideExecutorService()
        } returns MoreExecutors.newDirectExecutorService()
    }

    private val propertiesProvider: BuildConfigPropertiesProvider = mockk()

    private val oaidManager =
        OaidManagerImpl(logsManager, executorServiceProvider, propertiesProvider)

    private val app: Context = mockk {

    }

    @Test
    fun provideHmsOaid() {
        every { propertiesProvider.getManufacturer() } returns "huawei"

        mockkConstructor(OpenDeviceIdentifierClient::class) {
            every {
                constructedWith<OpenDeviceIdentifierClient>(
                    OfTypeMatcher<Context>(Context::class),
                    OfTypeMatcher<Long>(Long::class),
                    OfTypeMatcher<OpenDeviceIdentifierConnector>(OpenDeviceIdentifierConnector::class)
                ).getOaidInfo()
            } returns "oaid"

            oaidManager.init(app)

            val oaid = oaidManager.getOaid()

            Truth.assertThat(oaid).isEqualTo("oaid")

            verifyAll {
                executorServiceProvider.provideExecutorService()
                propertiesProvider.getManufacturer()
            }
        }
    }

    @Test
    fun provideHmsOaidNull() {
        every { propertiesProvider.getManufacturer() } returns "huawei"

        mockkConstructor(OpenDeviceIdentifierClient::class) {
            every {
                constructedWith<OpenDeviceIdentifierClient>(
                    OfTypeMatcher<Context>(Context::class),
                    OfTypeMatcher<Long>(Long::class),
                    OfTypeMatcher<OpenDeviceIdentifierConnector>(OpenDeviceIdentifierConnector::class)
                ).getOaidInfo()
            } returns null

            oaidManager.init(app)

            val oaid = oaidManager.getOaid()

            Truth.assertThat(oaid).isNull()

            verifyAll {
                executorServiceProvider.provideExecutorService()
                propertiesProvider.getManufacturer()
            }
        }
    }

    @Test
    fun provideNotHmsOaidNull() {
        every { propertiesProvider.getManufacturer() } returns "any"

        oaidManager.init(app)

        val oaid = oaidManager.getOaid()

        Truth.assertThat(oaid).isNull()

        verifyAll {
            executorServiceProvider.provideExecutorService()
            propertiesProvider.getManufacturer()
        }
    }
}