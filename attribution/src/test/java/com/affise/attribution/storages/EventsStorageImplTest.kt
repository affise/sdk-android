package com.affise.attribution.storages

import android.content.Context
import android.content.SharedPreferences
import com.affise.attribution.events.SerializedEvent
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.utils.timestamp
import com.google.common.truth.Truth
import io.mockk.*
import org.json.JSONObject
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Test for [EventsStorageImpl]
 */
class EventsStorageImplTest {

    @get:Rule
    val rootEventFolderRule = TemporaryFolder()
    private val rootEventFolder = "affise-events"

    private val key = "key"

    private val test1EventId = "test1EventId"
    private val test2EventId = "test2EventId"
    private val testEvent = "{\"key\":\"value\"}"
    private val testSerializedEvent = SerializedEvent(test1EventId, JSONObject(testEvent))

    private val editor: SharedPreferences.Editor = mockk {
        every { commit() } returns true
    }

    private val context: Context by lazy {
        mockk {
            every { getDir(rootEventFolder, Context.MODE_PRIVATE) } returns rootEventFolderRule.root
        }
    }

    private val logsManager: LogsManager = mockk()

    @Test
    fun `verify storeEvent`() {
        val storage = EventsStorageImpl(context, logsManager)

        storage.saveEvent(key, testSerializedEvent)

        val firstFileContent = rootEventFolderRule
            .root
            .listFiles { _, name ->
                name == key
            }
            ?.firstOrNull()
            ?.listFiles { _, name ->
                name == test1EventId
            }
            ?.firstOrNull()
            ?.getContent()

        Truth.assertThat(firstFileContent).isEqualTo(testEvent)
        Truth.assertThat(rootEventFolderRule.root.listFiles()?.size).isEqualTo(1)

        verifyAll {
            context.getDir(rootEventFolder, Context.MODE_PRIVATE)
            editor wasNot Called
            logsManager wasNot Called
        }
    }

    @Test
    fun `verify get events`() {
        val storage = EventsStorageImpl(context, logsManager)
        val events = storage.getEvents(key)

        Truth.assertThat(events.size).isEqualTo(0)

        verifyAll {
            context.getDir(rootEventFolder, Context.MODE_PRIVATE)
            editor wasNot Called
            logsManager wasNot Called
        }
    }

    @Test
    fun `verify get events 1`() {
        createTempFile(key, test1EventId, testEvent)

        val storage = EventsStorageImpl(context, logsManager)
        val events = storage.getEvents(key)

        Truth.assertThat(events.size).isEqualTo(1)
        Truth.assertThat(events.first().data.toString()).isEqualTo(testEvent)

        verifyAll {
            context.getDir(rootEventFolder, Context.MODE_PRIVATE)
            editor wasNot Called
            logsManager wasNot Called
        }
    }

    @Test
    fun `verify get events 1 invalid`() {
        val invalidFileName = "invalidFileName"
        val invalidFileContent = "{invalid json format}"

        every { logsManager.addSdkError(any()) } just Runs

        createTempFile(key, invalidFileName, invalidFileContent)

        val storage = EventsStorageImpl(context, logsManager)
        val events = storage.getEvents(key)

        Truth.assertThat(events.size).isEqualTo(0)

        verifyAll {
            context.getDir(rootEventFolder, Context.MODE_PRIVATE)
            logsManager.addSdkError(any())
            editor wasNot Called
        }
    }

    @Test
    fun `verify get event else key`() {
        createTempFile(key, test1EventId, testEvent)

        val storage = EventsStorageImpl(context, logsManager)
        val events = storage.getEvents("key2")

        Truth.assertThat(events.size).isEqualTo(0)

        verifyAll {
            context.getDir(rootEventFolder, Context.MODE_PRIVATE)
            editor wasNot Called
            logsManager wasNot Called
        }
    }

    @Test
    fun `verify get event in group old`() {
        createTempFile(key, test1EventId, testEvent)

        mockkStatic(::timestamp) {
            every {
                timestamp()
            } returns System.currentTimeMillis() + EVENTS_STORE_TIME

            val storage = EventsStorageImpl(context, logsManager)
            val events = storage.getEvents(key)

            Truth.assertThat(events.size).isEqualTo(0)

            verifyAll {
                context.getDir(rootEventFolder, Context.MODE_PRIVATE)
                timestamp()
                editor wasNot Called
                logsManager wasNot Called
            }
        }
    }

    @Test
    fun `verify deleteEvent`() {
        createTempFile(key, test1EventId, testEvent)

        var keyDirs = rootEventFolderRule.root.listFiles()

        Truth.assertThat(keyDirs?.size).isEqualTo(1)
        Truth.assertThat(keyDirs?.first()?.isDirectory).isEqualTo(true)

        var files = keyDirs?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        Truth.assertThat(files?.size).isEqualTo(1)

        val storage = EventsStorageImpl(context, logsManager)
        storage.deleteEvent(key, listOf(test1EventId))

        keyDirs = rootEventFolderRule.root.listFiles()

        Truth.assertThat(keyDirs?.size).isEqualTo(1)
        Truth.assertThat(keyDirs?.first()?.isDirectory).isEqualTo(true)

        files = keyDirs?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        Truth.assertThat(files?.size).isEqualTo(0)

        verifyAll {
            context.getDir(rootEventFolder, Context.MODE_PRIVATE)
            editor wasNot Called
            logsManager wasNot Called
        }
    }

    @Test
    fun `verify deleteEvent some`() {
        createTempFile(key, test1EventId, testEvent)
        createTempFile(key, test2EventId, testEvent)

        var keyDirs = rootEventFolderRule.root.listFiles()

        Truth.assertThat(keyDirs?.size).isEqualTo(1)
        Truth.assertThat(keyDirs?.first()?.isDirectory).isEqualTo(true)

        var files = keyDirs?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        Truth.assertThat(files?.size).isEqualTo(2)

        val storage = EventsStorageImpl(context, logsManager)
        storage.deleteEvent(key, listOf(test1EventId, test2EventId))

        keyDirs = rootEventFolderRule.root.listFiles()

        Truth.assertThat(keyDirs?.size).isEqualTo(1)
        Truth.assertThat(keyDirs?.first()?.isDirectory).isEqualTo(true)

        files = keyDirs?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        Truth.assertThat(files?.size).isEqualTo(0)

        verifyAll {
            context.getDir(rootEventFolder, Context.MODE_PRIVATE)
            editor wasNot Called
            logsManager wasNot Called
        }
    }

    @Test
    fun `verify deleteEvent 1 in some`() {
        createTempFile(key, test1EventId, testEvent)
        createTempFile("key2", test2EventId, testEvent)

        var keyDirs = rootEventFolderRule.root.listFiles()

        Truth.assertThat(keyDirs?.size).isEqualTo(2)
        Truth.assertThat(keyDirs?.get(0)?.isDirectory).isEqualTo(true)
        Truth.assertThat(keyDirs?.get(1)?.isDirectory).isEqualTo(true)

        var files = keyDirs?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        Truth.assertThat(files?.size).isEqualTo(2)

        val storage = EventsStorageImpl(context, logsManager)
        storage.deleteEvent(key, listOf(test1EventId, test2EventId))

        keyDirs = rootEventFolderRule.root.listFiles()

        Truth.assertThat(keyDirs?.size).isEqualTo(2)
        Truth.assertThat(keyDirs?.get(0)?.isDirectory).isEqualTo(true)
        Truth.assertThat(keyDirs?.get(1)?.isDirectory).isEqualTo(true)

        files = keyDirs?.flatMap { it.listFiles()?.toList() ?: emptyList() }

        Truth.assertThat(files?.size).isEqualTo(1)

        verifyAll {
            context.getDir(rootEventFolder, Context.MODE_PRIVATE)
            editor wasNot Called
            logsManager wasNot Called
        }
    }

    @Test
    fun clear() {
        createTempFile(key, test1EventId, testEvent)
        createTempFile("key2", test2EventId, testEvent)

        Truth.assertThat(rootEventFolderRule.root.listFiles()).isNotEmpty()

        val storage = EventsStorageImpl(context, logsManager)

        storage.clear()

        Truth.assertThat(rootEventFolderRule.root.listFiles()).isNull()

        verifyAll {
            context.getDir(rootEventFolder, Context.MODE_PRIVATE)
        }
    }

    private fun File.getContent(): String {
        return FileReader(this).use {
            it.readText()
        }
    }

    private fun createTempFile(dir: String, name: String, content: String) {
        val folder = File(rootEventFolderRule.root, dir)
        folder.mkdir()

        val file = File(folder, name)

        FileWriter(file).use {
            file.writeText(content)
        }
    }

    companion object {
        private const val EVENTS_STORE_TIME = 7 * 24 * 60 * 60 * 1000
    }
}