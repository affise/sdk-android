package com.affise.attribution.module.huawei.referrer

import com.affise.attribution.converter.Converter
import com.affise.attribution.logs.LogsManager
import org.json.JSONObject

/**
 * Converter from [String] to [HuaweiReferrerData]
 *
 * @property logsManager for error logging
 */
internal class StringToHuaweiReferrerDataConverter(
    private val logsManager: LogsManager?
) : Converter<String, HuaweiReferrerData?> {

    /**
     * Convert [from] String to json HuaweiReferrerData
     */
    override fun convert(from: String): HuaweiReferrerData? {
        return try {
            //Create JSONObject
            val j = JSONObject(from)

            //Create referrer data
            HuaweiReferrerData(
                installReferrer = j.optString(HuaweiReferrerData.KEYS.INSTALL_REFERRER),
                referrerClickTimestampSeconds = j.optLong(HuaweiReferrerData.KEYS.REFERRER_CLICK_TIMESTAMP_SECONDS),
                installBeginTimestampServerSeconds = j.optLong(HuaweiReferrerData.KEYS.INSTALL_BEGIN_TIMESTAMP_SERVER_SECONDS),
            )
        } catch (e: Exception) {
            //log error
            logsManager?.addSdkError(e)
            null
        }
    }
}