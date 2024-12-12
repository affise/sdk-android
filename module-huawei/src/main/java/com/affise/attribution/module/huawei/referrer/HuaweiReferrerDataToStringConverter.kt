package com.affise.attribution.module.huawei.referrer

import com.affise.attribution.converter.Converter
import org.json.JSONObject

/**
 * Converter from [HuaweiReferrerData] to [String]
 */
internal class HuaweiReferrerDataToStringConverter : Converter<HuaweiReferrerData, String> {

    /**
     * Convert [from] HuaweiReferrerData to String
     *
     * @return HuaweiReferrerData of string
     */
    override fun convert(from: HuaweiReferrerData) = mapOf(
        Pair(
            HuaweiReferrerData.KEYS.INSTALL_REFERRER,
            from.installReferrer
        ),
        Pair(
            HuaweiReferrerData.KEYS.REFERRER_CLICK_TIMESTAMP_SECONDS,
            from.referrerClickTimestampSeconds
        ),
        Pair(
            HuaweiReferrerData.KEYS.INSTALL_BEGIN_TIMESTAMP_SERVER_SECONDS,
            from.installBeginTimestampServerSeconds
        ),
    )
        .let(::JSONObject)
        .toString()
}