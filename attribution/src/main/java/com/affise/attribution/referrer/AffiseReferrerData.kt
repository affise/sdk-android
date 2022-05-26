package com.affise.attribution.referrer

/**
 * Model to store install referrer data
 *
 * @property installReferrer The referrer URL of the installed package.
 * @property referrerClickTimestampSeconds The client-side timestamp, in seconds, when the referrer click happened.
 * @property installBeginTimestampSeconds The client-side timestamp, in seconds, when app installation began.
 */
data class AffiseReferrerData(
    val installReferrer: String,
    val referrerClickTimestampSeconds: Long,
    val installBeginTimestampSeconds: Long,
    val installBeginTimestampServerSeconds: Long
) {
    object KEYS {
        const val installReferrer = "installReferrer"
        const val referrerClickTimestampSeconds = "referrerClickTimestampSeconds"
        const val installBeginTimestampSeconds = "installBeginTimestampSeconds"
        const val installBeginTimestampServerSeconds = "installBeginTimestampServerSeconds"
    }
}