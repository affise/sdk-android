package com.affise.attribution.storages

import android.content.Context
import com.affise.attribution.events.predefined.AffiseLog
import com.affise.attribution.logs.SerializedLog
import com.google.common.truth.Truth
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.json.JSONObject
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.io.FileWriter

/**
 * Test for [LogsStorageImpl]
 */
class LogsStorageImplTest {

    @get:Rule
    val folderRule = TemporaryFolder()

    private val key1 = "key1"
    private val key2 = "key2"

    private val context: Context by lazy {
        mockk {
            every { getDir(ROOT_DIR, Context.MODE_PRIVATE) } returns folderRule.root
        }
    }

    private val logType = LOG_NETWORK_DIR
    private val serializedLogId = "serializedLogId"
    private val serializedLogDataJson = "{\"test_key\":\"test_value\"}"
    private val serializedLogData = JSONObject(serializedLogDataJson)
    private val log: AffiseLog = mockk()
    private val serializedLog: SerializedLog = mockk {
        every { id } returns serializedLogId
        every { data } returns serializedLogData
        every { type } returns logType
    }

    private val logNetworkType = "affise_sdklog_network"
    private val logDeviceType = "affise_sdklog_ddata"
    private val logUserType = "affise_sdklog_udata"
    private val logSdkType = "affise_sdklog_main"

    private val allLogTypes = listOf(logNetworkType, logDeviceType, logUserType, logSdkType)

    @Test
    fun storeLog() {
        for (i in 0..10) {
            createTemp(key1, i.toString())
        }

        val storage = LogsStorageImpl(context)

        storage.saveLog(key1, LOG_NETWORK_DIR, serializedLog)

        val files1 = folderRule.root.listFiles()
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }
            ?.filter { it.name.contains(LOG_NETWORK_DIR) }
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }
            ?.filter { it.isFile }

        Truth.assertThat(files1?.size).isEqualTo(MAX_FILES_SIZE)

        verifyAll {
            log wasNot Called
            serializedLog.id
            serializedLog.data
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun getLogsEmpty() {
        val storage = LogsStorageImpl(context)

        val files = storage.getLogs(key1, allLogTypes)

        Truth.assertThat(files).isEmpty()

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun `create dirs of key`() {
        val storage = LogsStorageImpl(context)
        storage.getLogs(key1, allLogTypes)

        val filesSize = folderRule.root.listFiles()?.size

        Truth.assertThat(filesSize).isEqualTo(1)

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun `create dirs of types`() {
        val storage = LogsStorageImpl(context)
        storage.getLogs(key1, allLogTypes)

        val filesSize = folderRule.root.listFiles()
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }
            ?.size

        Truth.assertThat(filesSize).isEqualTo(4)

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun getLogsNotEmpty() {
        val serializedLogDataJson = "{\"test_key\":\"test_value\"}"
        createTemp(key1, "fileName", serializedLogDataJson)

        val storage = LogsStorageImpl(context)
        val files = storage.getLogs(key1, allLogTypes)
        val filesSize = files.size

        Truth.assertThat(filesSize).isEqualTo(1)

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun getLogsNotEmptyAndNotValid() {
        val serializedLogDataJson = "{\"test_key\":\"test_value\"}"
        createTemp(key1, "fileName", serializedLogDataJson)
        createTemp(key2, "fileNameSecond", "not valid json")

        val storage = LogsStorageImpl(context)
        val files = storage.getLogs(key1, allLogTypes)
        val filesSize = files.size

        Truth.assertThat(filesSize).isEqualTo(1)

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun deleteLog() {
        val serializedLogDataJson = "{\"test_key\":\"test_value\"}"
        createTemp(key1, "fileName", serializedLogDataJson)

        val storage = LogsStorageImpl(context)
        storage.deleteLogs(key1, allLogTypes, listOf("fileName"))

        val typeDirs = folderRule.root.listFiles()

        val typeDirsNames = typeDirs?.map { it.name }

        Truth.assertThat(typeDirsNames).contains(key1)

        val subTypeDirs = typeDirs
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        val subTypeDirsNames = subTypeDirs?.map { it.name }

        Truth.assertThat(subTypeDirs?.size).isEqualTo(4)

        Truth.assertThat(subTypeDirsNames).contains(LOG_NETWORK_DIR)
        Truth.assertThat(subTypeDirsNames).contains(LOG_DEVICE_DIR)
        Truth.assertThat(subTypeDirsNames).contains(LOG_USER_DIR)
        Truth.assertThat(subTypeDirsNames).contains(LOG_SDK_DIR)

        val filesSize = subTypeDirs
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        Truth.assertThat(filesSize).isEmpty()

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun deleteLogNotEmpty() {
        val serializedLogDataJson = "{\"test_key\":\"test_value\"}"
        createTemp(key1, "fileName", serializedLogDataJson)
        createTemp(key1, "fileNameSecond", serializedLogDataJson)

        val storage = LogsStorageImpl(context)
        storage.deleteLogs(key1, allLogTypes, listOf("fileName"))

        val typeDirs = folderRule.root.listFiles()

        val typeDirsNames = typeDirs?.map { it.name }

        Truth.assertThat(typeDirsNames).contains(key1)

        val subTypeDirs = typeDirs
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        val subTypeDirsNames = subTypeDirs?.map { it.name }

        Truth.assertThat(subTypeDirs?.size).isEqualTo(4)

        Truth.assertThat(subTypeDirsNames).contains(LOG_NETWORK_DIR)
        Truth.assertThat(subTypeDirsNames).contains(LOG_DEVICE_DIR)
        Truth.assertThat(subTypeDirsNames).contains(LOG_USER_DIR)
        Truth.assertThat(subTypeDirsNames).contains(LOG_SDK_DIR)

        val filesSize = subTypeDirs
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        Truth.assertThat(filesSize?.size).isEqualTo(1)

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun clear() {
        createTemp(key1, "fileName", serializedLogDataJson)
        createTemp(key2, "fileNameSecond", serializedLogDataJson)

        Truth.assertThat(folderRule.root.listFiles()).isNotEmpty()

        val storage = LogsStorageImpl(context)

        storage.clear()

        Truth.assertThat(folderRule.root.listFiles()).isNull()

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    private fun createTemp(
        dir: String,
        name: String,
        content: String = "",
        type: String = LOG_NETWORK_DIR,
    ) {
        val folderType = File(folderRule.root, dir)
            .apply { if (!exists()) mkdir() }

        val folder = File(folderType, type)
            .apply { if (!exists()) mkdir() }

        val file = File(folder, name)

        FileWriter(file).use {
            file.writeText(content)
        }
    }

    companion object {
        private const val ROOT_DIR = "affise-logs"
        private const val LOG_NETWORK_DIR = "affise_sdklog_network"
        private const val LOG_DEVICE_DIR = "affise_sdklog_ddata"
        private const val LOG_USER_DIR = "affise_sdklog_udata"
        private const val LOG_SDK_DIR = "affise_sdklog_main"
        private const val MAX_FILES_SIZE = 5
    }
}