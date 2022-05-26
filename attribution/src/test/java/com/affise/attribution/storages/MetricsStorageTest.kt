package com.affise.attribution.storages

import android.content.Context
import com.affise.attribution.converter.JsonObjectToMetricsEventConverter
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.io.FileWriter

/**
 * Test for [MetricsStorageImpl]
 */
class MetricsStorageTest {

    @get:Rule
    val folderRule = TemporaryFolder()

    private val key1 = "key"
    private val key2 = "key2"
    private val ignoreSubKey = "ignoreSubKey"
    private val subKey1 = "subKey"
    private val subKey2 = "subKey2"
    private val eventJsonText = "{event:event}"

    private val context: Context by lazy {
        mockk {
            every { getDir(ROOT_DIR, Context.MODE_PRIVATE) } returns folderRule.root
        }
    }

    private val converter = JsonObjectToMetricsEventConverter()

    @Test
    fun `get old events is empty`() {
        val storage = MetricsStorageImpl(context, converter)

        val events = storage.getMetricsEvents(key1, ignoreSubKey)

        Truth.assertThat(events).isEmpty()

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun `get old events is not empty`() {
        createTemp(key1, subKey1, "fileName", eventJsonText)

        val storage = MetricsStorageImpl(context, converter)

        val events = storage.getMetricsEvents(key1, ignoreSubKey)

        Truth.assertThat(events.size).isEqualTo(1)

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun `get old events is not empty with many days`() {
        createTemp(key1, subKey1, "fileName", eventJsonText)
        createTemp(key1, subKey2, "secondFileName", eventJsonText)

        val storage = MetricsStorageImpl(context, converter)

        val events = storage.getMetricsEvents(key1, ignoreSubKey)

        Truth.assertThat(events.size).isEqualTo(2)

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun `get old events is not empty with many events`() {
        createTemp(key1, subKey1, "fileName", eventJsonText)
        createTemp(key1, subKey1, "secondFileName", eventJsonText)

        val storage = MetricsStorageImpl(context, converter)

        val events = storage.getMetricsEvents(key1, ignoreSubKey)

        Truth.assertThat(events.size).isEqualTo(2)

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun `get old events is not empty with event in current day`() {
        createTemp(key1, subKey1, "fileName", eventJsonText)
        createTemp(key1, ignoreSubKey, "secondFileName", eventJsonText)

        val storage = MetricsStorageImpl(context, converter)

        val events = storage.getMetricsEvents(key1, ignoreSubKey)

        Truth.assertThat(events.size).isEqualTo(1)

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun `get old events is empty with event in current day`() {
        createTemp(key1, ignoreSubKey, "fileName", eventJsonText)

        val storage = MetricsStorageImpl(context, converter)

        val events = storage.getMetricsEvents(key1, ignoreSubKey)

        Truth.assertThat(events.size).isEqualTo(0)

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun `delete metrics`() {
        createTemp(key1, subKey1, "fileName", eventJsonText)
        createTemp(key1, subKey2, "fileName", eventJsonText)

        val filesBeforeDelete = folderRule.root.listFiles()
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        Truth.assertThat(filesBeforeDelete?.size).isEqualTo(2)

        val storage = MetricsStorageImpl(context, converter)

        storage.deleteMetrics(key1, ignoreSubKey)

        val filesAfterDelete = folderRule.root.listFiles()
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        Truth.assertThat(filesAfterDelete?.size).isEqualTo(0)

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun `not delete metrics in current day`() {
        createTemp(key1, ignoreSubKey, "fileName", eventJsonText)

        val filesBeforeDelete = folderRule.root.listFiles()
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        Truth.assertThat(filesBeforeDelete?.size).isEqualTo(1)

        val storage = MetricsStorageImpl(context, converter)

        storage.deleteMetrics(key1, ignoreSubKey)

        val filesAfterDelete = folderRule.root.listFiles()
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        Truth.assertThat(filesAfterDelete?.size).isEqualTo(1)

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun `not delete metrics another url`() {
        createTemp(key1, subKey1, "fileName", eventJsonText)

        val filesBeforeDelete = folderRule.root.listFiles()
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        Truth.assertThat(filesBeforeDelete?.size).isEqualTo(1)

        val storage = MetricsStorageImpl(context, converter)

        storage.deleteMetrics(key2, ignoreSubKey)

        val filesAfterDelete = folderRule.root.listFiles()
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        Truth.assertThat(filesAfterDelete?.size).isEqualTo(1)

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun `delete only old metrics current url with many url, many days and many events`() {
        createTemp(key1, subKey1, "fileName1", eventJsonText)
        createTemp(key2, subKey1, "fileName2", eventJsonText)
        createTemp(key1, subKey2, "fileName3", eventJsonText)
        createTemp(key2, subKey2, "fileName4", eventJsonText)
        createTemp(key1, ignoreSubKey, "fileName5", eventJsonText)
        createTemp(key2, ignoreSubKey, "fileName6", eventJsonText)

        val filesBeforeDelete = folderRule.root.listFiles()
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        Truth.assertThat(filesBeforeDelete?.size).isEqualTo(6)

        val storage = MetricsStorageImpl(context, converter)

        storage.deleteMetrics(key1, ignoreSubKey)

        val filesAfterDelete = folderRule.root.listFiles()
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }
            ?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        Truth.assertThat(filesAfterDelete?.size).isEqualTo(4)

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    @Test
    fun clear() {
        createTemp(key1, subKey1, "fileName1", eventJsonText)
        createTemp(key2, subKey1, "fileName2", eventJsonText)
        createTemp(key1, subKey2, "fileName3", eventJsonText)
        createTemp(key2, subKey2, "fileName4", eventJsonText)
        createTemp(key1, ignoreSubKey, "fileName5", eventJsonText)
        createTemp(key2, ignoreSubKey, "fileName6", eventJsonText)

        Truth.assertThat(folderRule.root.listFiles()).isNotEmpty()

        val storage = MetricsStorageImpl(context, converter)

        storage.clear()

        Truth.assertThat(folderRule.root.listFiles()).isNull()

        verifyAll {
            context.getDir(ROOT_DIR, Context.MODE_PRIVATE)
        }
    }

    private fun createTemp(
        urlDirName: String,
        dateDirName: String,
        fileName: String,
        content: String
    ) {
        val urlDir = File(folderRule.root, urlDirName)
            .apply { if (!exists()) mkdir() }

        val dateDir = File(urlDir, dateDirName)
            .apply { if (!exists()) mkdir() }

        val file = File(dateDir, fileName)

        FileWriter(file).use {
            file.writeText(content)
        }
    }

    companion object {
        private const val ROOT_DIR = "affise-metrics"
    }
}