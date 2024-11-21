package com.affise.attribution.module.rustore.referrer

import com.affise.attribution.converter.Converter
import com.affise.attribution.modules.rustore.RuStoreReferrerData
import org.json.JSONObject

/**
 * Converter from [RuStoreReferrerData] to [String]
 */
internal class RuStoreReferrerDataToStringConverter : Converter<RuStoreReferrerData, String> {

    /**
     * Convert [from] RuStoreReferrerData to String
     *
     * @return RuStoreReferrerData of string
     */
    override fun convert(from: RuStoreReferrerData) = mapOf(
        Pair(
            RuStoreReferrerData.KEYS.INSTALL_APP_TIMESTAMP,
            from.installAppTimestamp
        ),
        Pair(
            RuStoreReferrerData.KEYS.PACKAGE_NAME,
            from.packageName
        ),
        Pair(
            RuStoreReferrerData.KEYS.RECEIVED_TIMESTAMP,
            from.receivedTimestamp
        ),
        Pair(
            RuStoreReferrerData.KEYS.REFERRER_ID,
            from.referrerId
        ),
        Pair(
            RuStoreReferrerData.KEYS.VERSION_CODE,
            from.versionCode
        ),
    )
        .let(::JSONObject)
        .toString()
}