package com.affise.app.ui.fragments.settings

import androidx.lifecycle.LiveData

interface SettingsContract {
    interface ViewModel {
        val debugModeState: LiveData<Boolean>
        val debugLogRequestState: LiveData<Boolean>
        val debugLogResponseState: LiveData<Boolean>
        val offlineModeState: LiveData<Boolean>
        val backgroundTrackingModeState: LiveData<Boolean>
        val trackingModeState: LiveData<Boolean>
        val pushToken: LiveData<String>
        val affiseAppId: LiveData<String>
        val secretKey: LiveData<String>
        val domain: LiveData<String>
        fun setDebug(enabled: Boolean)
        fun debugLogRequest(show: Boolean)
        fun debugLogResponse(show: Boolean)
        fun setDomain(domain: String)
        fun setAffiseAppId(affiseAppId: String)
        fun setSecretKey(secretKey: String)
        fun onSetOfflineModeCheckboxClicked(isChecked: Boolean)
        fun onSetBackgroundTrackingModeCheckboxClicked(isChecked: Boolean)
        fun onSetTrackingModeCheckboxClicked(isChecked: Boolean)
        fun onGDPRForgetButtonClicked()
        fun onCrashApplicationAffiseButtonClicked()
        fun onCrashApplicationButtonClicked()
    }
}