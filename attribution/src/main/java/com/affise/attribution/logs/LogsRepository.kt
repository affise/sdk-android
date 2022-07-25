package com.affise.attribution.logs

import com.affise.attribution.events.predefined.AffiseLog

/**
 * Logs repository interface
 */
internal interface LogsRepository {
    /**
     * Has logs by [url] or not
     */
    fun hasLogs(url: String): Boolean

    /**
     * Store log for all [urls]
     */
    fun storeLog(log: AffiseLog, urls: List<String>)

    /**
     * Get log in current [url]
     */
    fun getLogs(url: String): List<SerializedLog>

    /**
     * Delete logs with [ids] in current [url]
     */
    fun deleteLogs(ids: List<String>, url: String)

    /**
     * Removes all logs
     */
    fun clear()
}