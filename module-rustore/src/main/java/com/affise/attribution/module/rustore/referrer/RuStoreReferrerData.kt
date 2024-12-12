package com.affise.attribution.module.rustore.referrer

/**
 * Model to RuStore install referrer data
 *
 * @property installAppTimestamp Timestamp, in seconds, when app installation began.
 * @property packageName Package name.
 * @property receivedTimestamp Timestamp, in seconds, when the referrer received.
 * @property referrerId Referrer Id.
 * @property versionCode Version code.
 *
 */
internal data class RuStoreReferrerData(
    val installAppTimestamp: Long,
    val packageName: String,
    val receivedTimestamp: Long,
    val referrerId: String,
    val versionCode: Long?
) {
    object KEYS {
        const val INSTALL_APP_TIMESTAMP = "installAppTimestamp"
        const val PACKAGE_NAME = "packageName"
        const val RECEIVED_TIMESTAMP = "receivedTimestamp"
        const val REFERRER_ID = "referrerId"
        const val VERSION_CODE = "versionCode"
    }
}
