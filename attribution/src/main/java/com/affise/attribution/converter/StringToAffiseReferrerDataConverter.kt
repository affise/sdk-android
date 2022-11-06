package com.affise.attribution.converter

import com.affise.attribution.logs.LogsManager
import com.affise.attribution.referrer.AffiseReferrerData
import org.json.JSONObject

/**
 * Converter from [String] to [AffiseReferrerData]
 *
 * @property logsManager for error logging
 */
class StringToAffiseReferrerDataConverter(
    private val logsManager: LogsManager
) : Converter<String, AffiseReferrerData?> {

    /**
     * Convert [from] String to json AffiseReferrerData
     */
    override fun convert(from: String): AffiseReferrerData? {
        return try {
            //Create JSONObject
            val j = JSONObject(from)

            //Create referrer data
            AffiseReferrerData(
                installReferrer = j.getString(AffiseReferrerData.KEYS.installReferrer),
                referrerClickTimestampSeconds = j.getLong(AffiseReferrerData.KEYS.referrerClickTimestampSeconds),
                installBeginTimestampSeconds = j.getLong(AffiseReferrerData.KEYS.installBeginTimestampSeconds),
                referrerClickTimestampServerSeconds = j.getLong(AffiseReferrerData.KEYS.referrerClickTimestampServerSeconds),
                installBeginTimestampServerSeconds = j.getLong(AffiseReferrerData.KEYS.installBeginTimestampServerSeconds),
                installVersion = j.getString(AffiseReferrerData.KEYS.installVersion),
                googlePlayInstantParam = j.getBoolean(AffiseReferrerData.KEYS.googlePlayInstantParam),
            )
        } catch (e: Exception) {
            //log error
            logsManager.addSdkError(e)
            null
        }
    }
}