package com.affise.attribution.logs

import com.affise.attribution.converter.Converter
import com.affise.attribution.events.predefined.AffiseLog
import com.affise.attribution.events.predefined.AffiseLogType
import com.affise.attribution.storages.LogsStorage
import com.google.common.truth.Truth
import io.mockk.*
import org.junit.Test

/**
 * Test for [LogsRepositoryImpl]
 */
class LogsRepositoryImplTest {

    private val url1 = "url1"
    private val url2 = "url2"

    private val url1Base64 = "url1Base64"
    private val url2Base64 = "url2Base64"

    private val testLogId = "testLogId"
    private val testLogType = "testLogType"
    private val testLogTypeBase64 = "testLogTypeBase64"

    private val logNetworkType = "affise_sdklog_network"
    private val logDeviceType = "affise_sdklog_ddata"
    private val logUserType = "affise_sdklog_udata"
    private val logSdkType = "affise_sdklog_main"

    private val logNetworkTypeBase64 = "affise_sdklog_network_Base64"
    private val logDeviceTypeBase64 = "affise_sdklog_ddata_Base64"
    private val logUserTypeBase64 = "affise_sdklog_udata_Base64"
    private val logSdkTypeBase64 = "affise_sdklog_main_Base64"

    private val allLogTypesBase64 =
        listOf(logNetworkTypeBase64, logDeviceTypeBase64, logUserTypeBase64, logSdkTypeBase64)

    private val testLogName: AffiseLogType = mockk {
        every { type } returns testLogType
    }

    private val testLog: AffiseLog = mockk {
        every { name } returns testLogName
    }

    private val testSerializedLog: SerializedLog = mockk()

    private val converter: Converter<AffiseLog, SerializedLog> = mockk {
        every { convert(testLog) } returns testSerializedLog
    }

    private val converterBase64: Converter<String, String> = mockk {
        every { convert(url1) } returns url1Base64
        every { convert(url2) } returns url2Base64
        every { convert(testLogType) } returns testLogTypeBase64
        every { convert(logNetworkType) } returns logNetworkTypeBase64
        every { convert(logDeviceType) } returns logDeviceTypeBase64
        every { convert(logUserType) } returns logUserTypeBase64
        every { convert(logSdkType) } returns logSdkTypeBase64
    }

    private val storage: LogsStorage = mockk {
        justRun { saveLog(url1Base64, testLogTypeBase64, testSerializedLog) }
        justRun { saveLog(url2Base64, testLogTypeBase64, testSerializedLog) }
        every { getLogs(url1Base64, allLogTypesBase64) } returns listOf(testSerializedLog)
        justRun { deleteLogs(url1Base64, allLogTypesBase64, listOf(testLogId)) }
        justRun { clear() }
    }

    private val logsManager: LogsManager = mockk()

    private val repository: LogsRepositoryImpl by lazy {
        LogsRepositoryImpl(converterBase64, converter, storage)
    }

    @Test
    fun `store event with 1 url`() {
        repository.storeLog(testLog, listOf(url1))

        verifyAll {
            testLogName.type
            testLog.name
            converterBase64.convert(testLogType)
            converterBase64.convert(url1)
            converter.convert(testLog)
            storage.saveLog(url1Base64, testLogTypeBase64, testSerializedLog)
            logsManager wasNot Called
        }
    }

    @Test
    fun `store event with some url`() {
        repository.storeLog(testLog, listOf(url1, url2))

        verifyAll {
            testLogName.type
            testLog.name
            converterBase64.convert(testLogType)
            converterBase64.convert(url1)
            converterBase64.convert(url2)
            converter.convert(testLog)
            storage.saveLog(url1Base64, testLogTypeBase64, testSerializedLog)
            storage.saveLog(url2Base64, testLogTypeBase64, testSerializedLog)
            logsManager wasNot Called
        }
    }

    @Test
    fun `get events`() {
        val events = repository.getLogs(url1)

        Truth.assertThat(events.size).isEqualTo(1)
        Truth.assertThat(events.first()).isEqualTo(testSerializedLog)

        verifyAll {
            testLogName wasNot Called
            testLog wasNot Called
            converter wasNot Called
            converterBase64.convert(url1)
            converterBase64.convert(logNetworkType)
            converterBase64.convert(logDeviceType)
            converterBase64.convert(logSdkType)
            converterBase64.convert(logUserType)
            storage.getLogs(url1Base64, allLogTypesBase64)
            logsManager wasNot Called
        }
    }

    @Test
    fun `delete events`() {
        repository.deleteLogs(listOf(testLogId), url1)

        verifyAll {
            testLogName wasNot Called
            testLog wasNot Called
            converter wasNot Called
            converterBase64.convert(url1)
            converterBase64.convert(logNetworkType)
            converterBase64.convert(logDeviceType)
            converterBase64.convert(logSdkType)
            converterBase64.convert(logUserType)
            storage.deleteLogs(url1Base64, allLogTypesBase64, listOf(testLogId))
            logsManager wasNot Called
        }
    }

    @Test
    fun clear() {
        repository.clear()

        verifyAll {
            testLogName wasNot Called
            testLog wasNot Called
            converter wasNot Called
            converterBase64 wasNot Called
            storage.clear()
            logsManager wasNot Called
        }
    }
}