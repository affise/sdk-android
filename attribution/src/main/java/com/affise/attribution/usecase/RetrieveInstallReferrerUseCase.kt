package com.affise.attribution.usecase

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import android.net.Uri
import com.affise.attribution.converter.Converter
import com.affise.attribution.converter.StringToAffiseReferrerDataConverter
import com.affise.attribution.deeplink.DeeplinkManager
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.referrer.AffiseReferrerData
import com.affise.attribution.referrer.AffiseReferrerDataToStringConverter
import com.affise.attribution.referrer.OnReferrerCallback
import com.affise.attribution.referrer.ReferrerKey
import com.affise.attribution.referrer.getPartnerKey
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails

class RetrieveInstallReferrerUseCase(
    private val preferences: SharedPreferences,
    private val toStringConverter: AffiseReferrerDataToStringConverter,
    private val toAffiseReferrerDataConverter: StringToAffiseReferrerDataConverter,
    private val app: Application,
    private val deeplinkManager: DeeplinkManager,
    private val logsManager: LogsManager,
    private val installReferrerToDeeplinkUriConverter: Converter<String, Uri?>
) {

    /**
     * Referrer client
     */
    private var referrerClient: InstallReferrerClient? = null

    private var onReferrerFinished: (() -> Unit)? = null

    private var callbacks: MutableMap<OnReferrerCallback, ReferrerKey?> = mutableMapOf()

    fun startInstallReferrerRetrieve(onFinished: (() -> Unit)? = null) {
        //Create referrer client
        referrerClient = InstallReferrerClient.newBuilder(app)
            .build()

        //Start connection
        referrerClient?.startConnection(object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                when (responseCode) {
                    InstallReferrerClient.InstallReferrerResponse.OK -> {
                        // Connection established.
                        try {
                            val data = referrerClient?.installReferrer ?: return

                            //Processing referrer details
                            processReferrerDetails(data)
                        } catch (throwable: Throwable) {
                            logsManager.addSdkError(
                                RuntimeException("Error read ReferrerClient")
                            )
                        }
                    }

                    InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
                        // API not available on the current Play Store app.
                    }

                    InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
                        // Connection couldn't be established.
                    }
                }
                onFinished?.invoke()
                onReferrerFinished?.invoke()
            }

            override fun onInstallReferrerServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

    /**
     * Get referrer value
     */
    fun getReferrer(callback: OnReferrerCallback?) {
        addCallback(callback)
        handleReferrer()
    }

    /**
     * Get referrer uri value by key
     */
    fun getReferrerValue(key: ReferrerKey, callback: OnReferrerCallback?) {
        addCallback(callback, key)
        handleReferrer()
    }

    private fun handleReferrer() {
        getInstallReferrer()?.let {
            handleReferrerCallback()
            return
        }

        onReferrerFinished = {
            handleReferrerCallback()
        }
    }

    @Synchronized
    private fun addCallback(callback: OnReferrerCallback?, key: ReferrerKey? = null) {
        callback?.let {
            callbacks[it] = key
        }
    }

    @Synchronized
    private fun handleReferrerCallback() {
        val referrer = getReferrer()
        val iterator = callbacks.entries.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            val result = if (item.value != null) {
                getReferrerValue(referrer, item.value)
            } else {
                referrer
            }
            item.key.handleReferrer(result)
            iterator.remove()
        }
    }

    private fun getReferrer(): String? {
        //Check referrer in partner_key
        val partnerKey = app.getPartnerKey()

        //if partner_key is empty or null use installReferrer
        return if (partnerKey.isNullOrEmpty()) {
            getInstallReferrer()?.installReferrer
        } else {
            partnerKey
        }
    }

    private fun getReferrerValue(referrer: String?, key: ReferrerKey?): String? {
        referrer ?: return null
        key ?: return null
        return Uri.parse("https://referrer/?$referrer")
            .getQueryParameter(key.type)
    }

    /**
     * Get install referrer
     * @return install referrer
     */
    fun getInstallReferrer() = preferences
        .getString(REFERRER_KEY, null)
        ?.let(toAffiseReferrerDataConverter::convert)

    /**
     * Processing referrer details
     */
    fun processReferrerDetails(data: ReferrerDetails) {
        if (!isDelayedDeeplinkProcessed()) {
            data.installReferrer
                ?.let(installReferrerToDeeplinkUriConverter::convert)
                ?.also {
                    deeplinkManager.handleDeeplink(it)
                }
            setDelayedDeeplinkProcessed()
        }

        //Generate referrer data
        AffiseReferrerData(
            installReferrer = data.installReferrer ?: "",
            referrerClickTimestampSeconds = data.referrerClickTimestampSeconds,
            installBeginTimestampSeconds = data.installBeginTimestampSeconds,
            referrerClickTimestampServerSeconds = data.referrerClickTimestampServerSeconds,
            installBeginTimestampServerSeconds = data.installBeginTimestampServerSeconds,
            installVersion = data.installVersion ?: "",
            googlePlayInstantParam = data.googlePlayInstantParam,
        )
            .let(toStringConverter::convert)
            .let(::storeToSharedPreferences)

        referrerClient?.endConnection()
    }

    /**
     * Save referrer data
     */
    @SuppressLint("ApplySharedPref")
    private fun storeToSharedPreferences(s: String) {
        val e = preferences.edit()
        e.putString(REFERRER_KEY, s)
        e.commit()
    }

    private fun isDelayedDeeplinkProcessed(): Boolean =
        preferences.getBoolean(DELAYED_DEEPLINK_PROCESSED_KEY, false)

    @SuppressLint("ApplySharedPref")
    private fun setDelayedDeeplinkProcessed() {
        preferences.edit()
            .apply {
                putBoolean(DELAYED_DEEPLINK_PROCESSED_KEY, true)
            }
            .commit()
    }

    companion object {
        private const val REFERRER_KEY = "referrer_data"
        private const val DELAYED_DEEPLINK_PROCESSED_KEY = "DELAYED_DEEPLINK_PROCESSED_KEY"
    }
}