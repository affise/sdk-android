package com.affise.attribution.storages

import android.annotation.SuppressLint
import android.content.Context
import com.affise.attribution.events.EventsParams
import com.affise.attribution.events.SerializedEvent
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.utils.timestamp
import org.json.JSONObject
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Storage of events
 *
 * @property context to retrieve app dir
 * @property logsManager for error logging
 */
internal class EventsStorageImpl(
    private val context: Context,
    private val logsManager: LogsManager,
) : EventsStorage {

    /**
     * Has save events by [key] or not
     */
    override fun hasEvents(key: String) = getEventsDirectory(key)
        .listFiles()
        ?.isNotEmpty()
        ?: false

    /**
     * Store [event] by [key]
     */
    @SuppressLint("ApplySharedPref")
    override fun saveEvent(key: String, event: SerializedEvent) {
        //Create file for event
        val file = File(getEventsDirectory(key), event.id)

        //Write event to file
        FileWriter(file).use {
            it.write(event.data.toString())
        }
    }

    /**
     * Get serialized events by [key]
     *
     * @return list of serialized events
     */
    override fun getEvents(key: String?): List<SerializedEvent> = getEventsDirectory(key)
        .listFiles()
        ?.asSequence()
        ?.filter { it.isFile }
        ?.filter { file ->
            //Filter old files
            (file.lastModified() > timestamp() - EventsParams.EVENTS_STORE_TIME).also { isActual ->
                if (!isActual) {
                    //Delete old files
                    file.runCatching { delete() }
                }
            }
        }
        ?.take(EventsParams.EVENTS_SEND_COUNT)
        ?.mapNotNull { file ->
            try {
                //Get data from file
                val data = FileReader(file).use {
                    JSONObject(it.readText())
                }
                //Create serializedEvent
                SerializedEvent(file.name, data)
            } catch (e: Exception) {
                //Remove file if not create event
                file.runCatching { delete() }

                logsManager.addSdkError(e)
                null
            }
        }
        ?.toList()
        ?: emptyList()

    /**
     * Delete event for [key] by [ids]
     */
    override fun deleteEvent(key: String?, ids: List<String>) {
        //Delete event
        getEventsDirectory(key)
            .listFiles { _, name ->
                //Get all files be name
                name in ids
            }
            ?.forEach {
                //Remove file
                it.runCatching { delete() }
            }
    }

    /**
     * Removes all events
     */
    override fun clear() {
        context.getDir(EventsParams.EVENTS_DIR_NAME, Context.MODE_PRIVATE).deleteRecursively()
    }

    /**
     * Get events directory by [key] (subDir)
     * @return dir for events
     */
    private fun getEventsDirectory(key: String? = null): File {
        //Get root events dir
        val eventDir = context.getDir(EventsParams.EVENTS_DIR_NAME, Context.MODE_PRIVATE)
            .apply {
                //Create eventDir if doesn't exists
                if (!exists()) mkdir()
            }

        return key
            ?.takeIf {
                //Check key
                it.isNotEmpty()
            }
            ?.let {
                //Get subdirectory by key
                File(eventDir, it)
            }
            ?.apply {
                //Create subdirectory if doesn't exists
                if (!exists()) mkdir()
            }
            ?: eventDir
    }
}