package com.affise.attribution.module.meta.usecase

import android.app.Application
import android.database.Cursor
import android.net.Uri
import com.affise.attribution.module.meta.utils.resolveAuthority
import com.affise.attribution.module.meta.utils.toContentUri
import com.affise.attribution.settings.AffiseConfig


internal class MetaReferrerUseCaseImpl(
    private val app: Application?,
    configValues: Map<AffiseConfig, Any>?,
) : MetaReferrerUseCase {

    private val fbAppId: String? = configValues?.get(AffiseConfig.FbAppId) as? String

    private var metaReferrer: Map<String, Any>? = null

    override fun metaReferrer(): Map<String, Any> {
        if (!metaReferrer.isNullOrEmpty()) return metaReferrer ?: emptyMap()
        metaReferrer = processReferrerDetails()
        return metaReferrer ?: emptyMap()
    }

    private fun processReferrerDetails(): Map<String, Any>? {
        app ?: return null
        var cursor: Cursor? = null
        try {
            val projection = arrayOf(
                INSTALL_REFERRER,
                IS_CT,
                ACTUAL_TIMESTAMP
            )

            // This IF statement queries the facebook app first.
            // To query from the instagram app first, change the sequence of the IF statement
            val providerUri: Uri? = when {
                app.resolveAuthority(FB_REFERRER_PROVIDER) -> "$FB_REFERRER_PROVIDER/$fbAppId"
                app.resolveAuthority(INSTAGRAM_REFERRER_PROVIDER) -> "$INSTAGRAM_REFERRER_PROVIDER/$fbAppId"
                app.resolveAuthority(FB_LITE_REFERRER_PROVIDER) -> "$FB_LITE_REFERRER_PROVIDER/$fbAppId"
                else -> null
            }?.toContentUri()

            providerUri ?: return null

            cursor = app.contentResolver.query(providerUri, projection, null, null, null)
            if (cursor == null || !cursor.moveToFirst()) {
                return null
            }

            val installReferrerIndex = cursor.getColumnIndex(INSTALL_REFERRER)
            val timestampIndex = cursor.getColumnIndex(ACTUAL_TIMESTAMP)
            val isCTIndex = cursor.getColumnIndex(IS_CT)
            val installReferrer = cursor.getString(installReferrerIndex) // serialized and encrypted attribution details

            val actualTimestamp = cursor.getLong(timestampIndex) // timestamp in seconds for click/impression
            val isCT = cursor.getInt(isCTIndex) // VT or CT, 0 = VT, 1 = CT

            return mapOf(
                INSTALL_REFERRER to installReferrer,
                ACTUAL_TIMESTAMP to actualTimestamp,
                IS_CT to isCT,
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            cursor?.close()
        }
    }

    companion object {
        private const val FB_REFERRER_PROVIDER = "com.facebook.katana.provider.InstallReferrerProvider"
        private const val INSTAGRAM_REFERRER_PROVIDER = "com.instagram.contentprovider.InstallReferrerProvider"
        private const val FB_LITE_REFERRER_PROVIDER = "com.facebook.lite.provider.InstallReferrerProvider"

        const val INSTALL_REFERRER = "install_referrer"
        const val IS_CT = "is_ct"
        const val ACTUAL_TIMESTAMP = "actual_timestamp"
    }
}