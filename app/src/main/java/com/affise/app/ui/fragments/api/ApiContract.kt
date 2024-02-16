package com.affise.app.ui.fragments.api

import androidx.lifecycle.LiveData
import com.affise.attribution.referrer.ReferrerKey

interface ApiContract {
    interface ViewModel {
        val offlineModeState: LiveData<Boolean>
        val backgroundTrackingModeState: LiveData<Boolean>
        val trackingModeState: LiveData<Boolean>
        val output: LiveData<String>
        fun onDebugValidateClicked()
        fun onRegisterDeeplinkClicked()
        fun onGetReferrerClicked()
        fun onGetReferrerValueClicked(key: ReferrerKey)
        fun onStatusClicked()
        fun onRandomPushTokenClicked()
        fun onRandomUserIdClicked()
        fun onRandomDeviceIdClicked()
        fun onProvidersClicked()
        fun onSetOfflineModeCheckboxClicked(isChecked: Boolean)
        fun onSetBackgroundTrackingModeCheckboxClicked(isChecked: Boolean)
        fun onSetTrackingModeCheckboxClicked(isChecked: Boolean)
        fun clearOutput()
    }
}