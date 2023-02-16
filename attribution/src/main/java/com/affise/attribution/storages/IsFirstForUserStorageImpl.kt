package com.affise.attribution.storages

import android.content.Context
import com.affise.attribution.events.EventsParams
import com.affise.attribution.logs.LogsManager
import java.io.File

/**
 * Storage of already send events
 *
 * @property context to retrieve app dir
 * @property logsManager for error logging
 */
internal class IsFirstForUserStorageImpl(
    private val context: Context,
    private val logsManager: LogsManager,
) : IsFirstForUserStorage {


    override fun add(eventClass: String) {
        getEventsFile().appendText("${eventClass}\n")
    }

    override fun getEventsNames(): List<String> {
        try {
            return getEventsFile().readLines().map { it.trim() }
        } catch (e: Exception) {
            logsManager.addSdkError(e)
        }

        return emptyList()
    }

    /**
     * Get events sent file
     * @return file for events sent
     */
    private fun getEventsFile(): File {
        //Get root events dir
        val eventDir = context.getDir(EventsParams.EVENTS_DIR_NAME, Context.MODE_PRIVATE)
            .apply {
                //Create eventDir if doesn't exists
                if (!exists()) mkdir()
            }

        val file = File(eventDir, NAME).apply {
            if (!exists()) createNewFile()
        }

        return file
    }

    companion object {
        private const val NAME = "first-for-user"
    }
}