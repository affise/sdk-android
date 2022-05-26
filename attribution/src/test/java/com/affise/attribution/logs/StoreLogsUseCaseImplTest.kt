package com.affise.attribution.logs

import com.affise.attribution.events.predefined.AffiseLog
import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.network.CloudConfig
import com.google.common.util.concurrent.MoreExecutors
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class StoreLogsUseCaseImplTest {

    private val urls: List<String> = listOf("https://url1", "https://url2")

    private val log: AffiseLog = mockk()

    private val provider: ExecutorServiceProvider = mockk {
        every {
            provideExecutorService()
        } returns MoreExecutors.newDirectExecutorService()
    }

    private val repository: LogsRepository = mockk {
        every { storeLog(log, urls) } just Runs
    }

    private val useCase = StoreLogsUseCaseImpl(provider, repository)

    @Before
    fun setup() {
        mockkObject(CloudConfig)
        every { CloudConfig.getUrls() } returns urls
    }

    @After
    fun tearDown() {
        unmockkObject(CloudConfig)
    }

    @Test
    fun storeLog() {
        useCase.storeLog(log)

        verifyAll {
            provider.provideExecutorService()
            repository.storeLog(log, urls)
        }
    }
}