package com.affise.attribution.referrer

import com.affise.attribution.converter.Converter
import org.json.JSONObject

/**
 * Converter from [AffiseReferrerData] to [String]
 */
class AffiseReferrerDataToStringConverter : Converter<AffiseReferrerData, String> {

    /**
     * Convert [from] AffiseReferrerData to String
     *
     * @return AffiseReferrerData of string
     */
    override fun convert(from: AffiseReferrerData) = mapOf(
        Pair(
            AffiseReferrerData.KEYS.installReferrer,
            from.installReferrer
        ),
        Pair(
            AffiseReferrerData.KEYS.referrerClickTimestampSeconds,
            from.referrerClickTimestampSeconds
        ),
        Pair(
            AffiseReferrerData.KEYS.installBeginTimestampSeconds,
            from.installBeginTimestampSeconds
        ),
        Pair(
            AffiseReferrerData.KEYS.referrerClickTimestampServerSeconds,
            from.referrerClickTimestampServerSeconds
        ),
        Pair(
            AffiseReferrerData.KEYS.installBeginTimestampServerSeconds,
            from.installBeginTimestampServerSeconds
        ),
        Pair(
            AffiseReferrerData.KEYS.installVersion,
            from.installVersion
        ),
        Pair(
            AffiseReferrerData.KEYS.googlePlayInstantParam,
            from.googlePlayInstantParam
        ),
    )
        .let(::JSONObject)
        .toString()
}