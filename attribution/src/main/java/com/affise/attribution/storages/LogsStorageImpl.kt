package com.affise.attribution.storages

import android.content.Context
import com.affise.attribution.logs.SerializedLog
import org.json.JSONObject
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Storage of logs
 *
 * @property context to retrieve app dir
 */
internal class LogsStorageImpl(
    private val context: Context
) : LogsStorage {

    /**
     * Has logs by [url] or not
     */
    override fun hasLogs(key: String, subKeys: List<String>): Boolean = subKeys
        .any { subKey ->
            getLogsDirectory(key, subKey)
                .listFiles()
                ?.isNotEmpty()
                ?: false
        }

    /**
     * Store not send [log] by [key] and [subKey]
     */
    override fun saveLog(key: String, subKey: String, log: SerializedLog) {
        //Create log dir
        val logsDir = getLogsDirectory(key, subKey)

        //Delete old logs
        logsDir.listFiles()
            ?.sortedBy(File::lastModified)
            ?.dropLast(LOGS_MAX_COUNT - 1)
            ?.forEach {
                it.runCatching { delete() }
            }

        //Save logs
        //Create file for log
        val file = File(logsDir, log.id)

        //Write log to file
        FileWriter(file).use {
            it.write(log.data.toString())
        }
    }

    /**
     * Get logs by [key] and [subKeys]
     */
    override fun getLogs(key: String, subKeys: List<String>): List<SerializedLog> =
        subKeys.flatMap { subKey ->
            getLogsDirectory(key, subKey)
                .listFiles()
                ?.asSequence()
                ?.filter {
                    //Get only file
                    it.isFile
                }
                ?.mapNotNull { file ->
                    try {
                        //Get data from file
                        val data = FileReader(file).use {
                            JSONObject(it.readText())
                        }
                        //Create serializedLog
                        SerializedLog(file.name, subKey, data)
                    } catch (e: Exception) {
                        //Remove file if not create log
                        file.runCatching { delete() }

                        null
                    }
                }
                ?.toList()
                ?: emptyList()
        }

    /**
     * Delete logs by [key] and [subKeys] only in [ids]
     */
    override fun deleteLogs(key: String, subKeys: List<String>, ids: List<String>) {
        subKeys.map { subKey ->
            getLogsDirectory(key, subKey)
        }
            .forEach {
                it.listFiles { _, name -> name in ids }
                    ?.forEach { file ->
                        //Remove file
                        file.runCatching { delete() }
                    }
            }
    }

    /**
     * Removes all logs
     */
    override fun clear() {
        context.getDir(LOGS_DIR_NAME, Context.MODE_PRIVATE).deleteRecursively()
    }

    /**
     * Get logs directory be [key] and [subKey]
     */
    private fun getLogsDirectory(key: String, subKey: String? = null): File {
        val logDir = context.getDir(LOGS_DIR_NAME, Context.MODE_PRIVATE)
            .apply {
                //Create logDir if doesn't exists
                if (!exists()) mkdir()
            }

        val logNameDir = key
            .takeIf {
                it.isNotEmpty()
            }
            ?.let {
                File(logDir, key)
            }
            ?.apply {
                //Create logNameDir if doesn't exists
                if (!exists()) mkdir()
            }
            ?: logDir

        return subKey
            .takeIf {
                !it.isNullOrEmpty()
            }
            ?.let {
                File(logNameDir, it)
            }
            ?.apply {
                //Create keyDir if doesn't exists
                if (!exists()) mkdir()
            }
            ?: logNameDir
    }

    companion object {
        private const val LOGS_DIR_NAME = "affise-logs"
        private const val LOGS_MAX_COUNT = 5
    }
}