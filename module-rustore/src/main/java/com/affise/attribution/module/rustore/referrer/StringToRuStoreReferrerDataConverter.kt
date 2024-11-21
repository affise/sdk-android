package com.affise.attribution.module.rustore.referrer

import com.affise.attribution.converter.Converter
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.modules.rustore.RuStoreReferrerData
import org.json.JSONObject

/**
 * Converter from [String] to [RuStoreReferrerData]
 *
 * @property logsManager for error logging
 */
internal class StringToRuStoreReferrerDataConverter(
    private val logsManager: LogsManager?
) : Converter<String, RuStoreReferrerData?> {

    /**
     * Convert [from] String to json RuStoreReferrerData
     */
    override fun convert(from: String): RuStoreReferrerData? {
        return try {
            //Create JSONObject
            val j = JSONObject(from)

            //Create referrer data
            RuStoreReferrerData(
                installAppTimestamp = j.optLong(RuStoreReferrerData.KEYS.INSTALL_APP_TIMESTAMP),
                packageName = j.optString(RuStoreReferrerData.KEYS.PACKAGE_NAME),
                receivedTimestamp = j.optLong(RuStoreReferrerData.KEYS.RECEIVED_TIMESTAMP),
                referrerId = j.optString(RuStoreReferrerData.KEYS.REFERRER_ID),
                versionCode = j.optLong(RuStoreReferrerData.KEYS.VERSION_CODE),
            )
        } catch (e: Exception) {
            //log error
            logsManager?.addSdkError(e)
            null
        }
    }
}