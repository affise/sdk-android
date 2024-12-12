package com.affise.attribution.module.huawei.referrer

internal data class HuaweiReferrerData(
    val installReferrer: String,
    val referrerClickTimestampSeconds: Long,
    val installBeginTimestampServerSeconds: Long,
) {
    object KEYS {
        const val INSTALL_REFERRER = "installReferrer"
        const val REFERRER_CLICK_TIMESTAMP_SECONDS = "referrerClickTimestampSeconds"
        const val INSTALL_BEGIN_TIMESTAMP_SERVER_SECONDS = "installBeginTimestampServerSeconds"
    }
}