package com.affise.attribution.storages

import android.content.Context
import com.affise.attribution.converter.JsonObjectToMetricsEventConverter
import com.affise.attribution.metrics.MetricsEvent
import com.affise.attribution.utils.generateUUID
import org.json.JSONObject
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Storage of metrics
 *
 * @property context use for getting access storage on device
 */
internal class MetricsStorageImpl(
    private val context: Context,
    private val converter: JsonObjectToMetricsEventConverter
) : MetricsStorage {

    /**
     * Has metrics by [key] or not
     */
    override fun hasMetrics(key: String, ignoreSubKey: String) = getMetricsDirectory(key)
        .listFiles()
        ?.filter { file ->
            //Don't get events in current day
            file.name != ignoreSubKey
        }?.any {
            it.listFiles()?.isNotEmpty() ?: false
        } ?: false

    /**
     * Get metrics events by [key] excluding [ignoreSubKey]
     */
    override fun getMetricsEvents(
        key: String,
        ignoreSubKey: String
    ): List<MetricsEvent> = getMetricsDirectory(key)
        .listFiles()
        ?.filter { file ->
            //Don't get events in current day
            file.name != ignoreSubKey
        }
        ?.flatMap {
            it.listFiles()?.toList() ?: emptyList()
        }
        ?.mapNotNull { file ->
            try {
                //Read file
                FileReader(file).use {
                    JSONObject(it.readText())
                }.let {
                    //Convert to metrics event
                    converter.convert(it)
                }
            } catch (e: Throwable) {
                //Remove file if not create metrics event
                file.runCatching { delete() }

                null
            }
        }
        ?: emptyList()

    /**
     * Get metrics event by [key] and [subKey]
     */
    override fun getMetricsEvent(
        key: String,
        subKey: String
    ): MetricsEvent? = getMetricsDirectory(key, subKey)
        .listFiles()
        ?.firstOrNull()
        ?.let { file ->
            try {
                //Read file
                val saveData = FileReader(file).use {
                    JSONObject(it.readText())
                }

                //Delete file
                file.runCatching { delete() }

                converter.convert(saveData)
            } catch (e: Exception) {
                //Remove file if not create log
                if (file.exists()) {
                    file.runCatching { delete() }
                }

                null
            }
        }

    /**
     * Save metrics [event] by [key] and [subKey]
     */
    override fun saveMetricsEvent(key: String, subKey: String, event: MetricsEvent) {
        //Get metrics event dir for current day
        val dir = getMetricsDirectory(key, subKey)

        //Delete old metrics event
        dir.listFiles()
            ?.sortedBy(File::lastModified)
            ?.dropLast(METRICS_MAX_COUNT - 1)
            ?.forEach {
                it.runCatching { deleteRecursively() }
            }

        //Generate new file
        val file = File(dir, generateUUID().toString())

        //Save metrics event to file
        FileWriter(file).use {
            it.write(event.serialize().toString())
        }
    }

    /**
     * Delete metrics event by [key] excluding [ignoreSubKey]
     */
    override fun deleteMetrics(key: String, ignoreSubKey: String) {
        getMetricsDirectory(key)
            .listFiles()
            ?.filter { file ->
                //Don't get events in current day
                file.name != ignoreSubKey
            }
            ?.forEach { file ->
                //Delete
                file.runCatching { deleteRecursively() }
            }
    }

    /**
     * Removes all metrics events
     */
    override fun clear() {
        context.getDir(METRICS_DIR_NAME, Context.MODE_PRIVATE).deleteRecursively()
    }

    /**
     * Get logs directory by [key] and [subKey]
     */
    private fun getMetricsDirectory(key: String, subKey: String? = null): File {
        //Get or create metricsDir
        val metricsDir = context.getDir(METRICS_DIR_NAME, Context.MODE_PRIVATE)
            .apply {
                if (!exists()) mkdir()
            }

        //Get or create metricsUrlDir
        val metricsUrlDir = File(metricsDir, key)
            .apply {
                if (!exists()) mkdir()
            }

        //Get dir by url or key
        return subKey
            ?.takeIf {
                it.isNotEmpty()
            }
            ?.let {
                File(metricsUrlDir, it)
            }
            ?.apply {
                if (!exists()) mkdir()
            }
            ?: metricsUrlDir
    }

    companion object {
        private const val METRICS_DIR_NAME = "affise-metrics"
        private const val METRICS_MAX_COUNT = 30
    }
}