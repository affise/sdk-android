package com.affise.attribution.logs

import com.affise.attribution.converter.Converter
import com.affise.attribution.events.predefined.AffiseLog
import com.affise.attribution.events.predefined.AffiseLogType
import com.affise.attribution.storages.LogsStorage

/**
 * Repository for logs models
 *
 * @property converterToBase64 convert string to encoding Base64 string
 * @property converterToSerializedLog to convert Event to SerializedEvent
 * @property logsStorage storage of logs
 */
internal class LogsRepositoryImpl(
    private val converterToBase64: Converter<String, String>,
    private val converterToSerializedLog: Converter<AffiseLog, SerializedLog>,
    private val logsStorage: LogsStorage
) : LogsRepository {

    /**
     * Has logs by [url] or not
     */
    override fun hasLogs(url: String): Boolean = logsStorage.hasLogs(
        converterToBase64.convert(url),
        AffiseLogType.values().map { converterToBase64.convert(it.type) }
    )

    /**
     * Store [log]
     */
    @Synchronized
    override fun storeLog(log: AffiseLog, urls: List<String>) {
        urls.forEach { url ->
            logsStorage.saveLog(
                converterToBase64.convert(url),
                converterToBase64.convert(log.name.type),
                converterToSerializedLog.convert(log)
            )
        }
    }

    /**
     * Get logs by [url]
     * @return logs
     */
    override fun getLogs(url: String): List<SerializedLog> = logsStorage.getLogs(
        converterToBase64.convert(url),
        AffiseLogType.values().map { converterToBase64.convert(it.type) }
    )

    /**
     * Removes all log by [ids] in [url]
     */
    override fun deleteLogs(ids: List<String>, url: String) {
        logsStorage.deleteLogs(
            converterToBase64.convert(url),
            AffiseLogType.values().map { converterToBase64.convert(it.type) },
            ids
        )
    }

    /**
     * Removes all Logs
     */
    override fun clear() {
        logsStorage.clear()
    }
}