package com.affise.app.ui.fragments.settings

import androidx.lifecycle.LiveData

interface SettingsContract {
    interface ViewModel {
        val offlineModeState: LiveData<Boolean>
        val backgroundTrackingModeState: LiveData<Boolean>
        val trackingModeState: LiveData<Boolean>
        val pushToken: LiveData<String>
        fun setSecretId(secretId: String)
        fun onSetOfflineModeCheckboxClicked(isChecked: Boolean)
        fun onSetBackgroundTrackingModeCheckboxClicked(isChecked: Boolean)
        fun onSetTrackingModeCheckboxClicked(isChecked: Boolean)
        fun onGDPRForgetButtonClicked()
        fun onCrashApplicationAffiseButtonClicked()
        fun onCrashApplicationButtonClicked()
    }
}