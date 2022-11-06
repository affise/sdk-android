package com.affise.attribution.referrer

/**
 * Model to store install referrer data
 *
 * @property installReferrer The referrer URL of the installed package.
 * @property referrerClickTimestampSeconds The client-side timestamp, in seconds, when the referrer click happened.
 * @property installBeginTimestampSeconds The client-side timestamp, in seconds, when app installation began.
 * @property referrerClickTimestampServerSeconds The server-side timestamp, in seconds, when the referrer click happened.
 * @property installBeginTimestampServerSeconds The server-side timestamp, in seconds, when app installation began.
 * @property installVersion The app's version at the time when the app was first installed.
 * @property googlePlayInstantParam Indicates whether your app's instant experience was launched within the past 7 days.
 *
 */
data class AffiseReferrerData(
    val installReferrer: String,
    val referrerClickTimestampSeconds: Long,
    val installBeginTimestampSeconds: Long,
    val referrerClickTimestampServerSeconds: Long,
    val installBeginTimestampServerSeconds: Long,
    val installVersion: String,
    val googlePlayInstantParam: Boolean,
) {
    object KEYS {
        const val installReferrer = "installReferrer"
        const val referrerClickTimestampSeconds = "referrerClickTimestampSeconds"
        const val installBeginTimestampSeconds = "installBeginTimestampSeconds"
        const val referrerClickTimestampServerSeconds = "referrerClickTimestampServerSeconds"
        const val installBeginTimestampServerSeconds = "installBeginTimestampServerSeconds"
        const val installVersion = "installVersion"
        const val googlePlayInstantParam = "googlePlayInstantParam"
    }
}