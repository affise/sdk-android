package com.affise.attribution.storages

import com.affise.attribution.logs.SerializedLog

internal interface LogsStorage {
    fun hasLogs(key: String, subKeys: List<String>): Boolean
    fun saveLog(key: String, subKey: String, log: SerializedLog)
    fun getLogs(key: String, subKeys: List<String>): List<SerializedLog>
    fun deleteLogs(key: String, subKeys: List<String>, ids: List<String>)
    fun clear()
}