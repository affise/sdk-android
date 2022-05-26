package com.affise.attribution.logs

import io.mockk.mockk
import io.mockk.every
import io.mockk.just
import io.mockk.verifyAll
import io.mockk.Runs
import org.junit.Test

class LogsManagerImplTest {

    private val testThrowable = Throwable()

    private val storeLogsUseCase: StoreLogsUseCase = mockk {
        every {
            storeLog(any())
        } just Runs
    }

    private val logsManager = LogsManagerImpl(storeLogsUseCase)

    @Test
    fun addNetworkError() {
        logsManager.addNetworkError(testThrowable)

        verifyAll {
            storeLogsUseCase.storeLog(any())
        }

    }

    @Test
    fun addDeviceError() {
        logsManager.addDeviceError(testThrowable)

        verifyAll {
            storeLogsUseCase.storeLog(any())
        }

    }

    @Test
    fun addUserError() {
        logsManager.addUserError(testThrowable)

        verifyAll {
            storeLogsUseCase.storeLog(any())
        }

    }

    @Test
    fun addSdkError() {
        logsManager.addSdkError(testThrowable)

        verifyAll {
            storeLogsUseCase.storeLog(any())
        }

    }

}