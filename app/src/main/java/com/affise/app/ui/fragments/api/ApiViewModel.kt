package com.affise.app.ui.fragments.api

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.affise.attribution.Affise
import com.affise.attribution.modules.AffiseModules
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.referrer.ReferrerKey
import com.affise.attribution.utils.generateUUID
import javax.inject.Inject

class ApiViewModel @Inject constructor(
    private val preferences: SharedPreferences
) : ViewModel(), ApiContract.ViewModel {
    private val _offlineModeState = MutableLiveData<Boolean>()
    private val _backgroundTrackingModeState = MutableLiveData<Boolean>()
    private val _trackingModeState = MutableLiveData<Boolean>()
    private val _output = MutableLiveData<String>()

    override val offlineModeState: LiveData<Boolean>
        get() = _offlineModeState

    override val backgroundTrackingModeState: LiveData<Boolean>
        get() = _backgroundTrackingModeState

    override val trackingModeState: LiveData<Boolean>
        get() = _trackingModeState

    override val output: LiveData<String>
        get() = _output

    init {
        _output.value = ""
        _offlineModeState.value = Affise.isOfflineModeEnabled()
        _backgroundTrackingModeState.value = Affise.isBackgroundTrackingEnabled()
        _trackingModeState.value = Affise.isTrackingEnabled()
    }

    override fun onDebugValidateClicked() {
        Affise.Debug.validate {
            output("Validate: $it")
        }
    }

    override fun onRegisterDeeplinkClicked() {
        Affise.registerDeeplinkCallback {
            output("Deeplink: $it")
            false
        }
    }

    override fun onGetReferrerClicked() {
        Affise.getReferrer {
            output("GetReferrer: $it")
        }
    }

    override fun onGetReferrerValueClicked(key: ReferrerKey) {
        Affise.getReferrerValue(key) {
            output("GetReferrerValue: $key = $it")
        }
    }

    override fun onStatusClicked() {
        Affise.getStatus(AffiseModules.Status) { status ->
            val statusData = if (status.isNotEmpty()) {
                "\n- ${status.joinToString { "${it.key} = ${it.value}" }}"
            } else {
                ""
            }
            output("GetStatus: count = ${status.count()}$statusData")
        }
    }

    override fun onRandomPushTokenClicked() {
        val token = generateUUID().toString()
        Affise.addPushToken(token)
        output("AddPushToken: $token")
    }

    override fun onRandomUserIdClicked() {
        val value = Affise.getRandomUserId()
        output("GetRandomUserId: $value")
    }

    override fun onRandomDeviceIdClicked() {
        val value = Affise.getRandomDeviceId()
        output("GetRandomDeviceId: $value")
    }

    override fun onProvidersClicked() {
        val providers = Affise.getProviders()
        val key = ProviderType.AFFISE_APP_TOKEN
        if (providers.containsKey(key)) {
            output("GetProviders: ${key.provider} = ${providers[key]}")
        } else {
            output("GetProviders: key = ${key.provider} not found")
        }
    }

    override fun onSetOfflineModeCheckboxClicked(isChecked: Boolean) {
        Affise.setOfflineModeEnabled(isChecked)
        _offlineModeState.value = Affise.isOfflineModeEnabled()
        output("IsOfflineModeEnabled: ${_offlineModeState.value}")
    }

    override fun onSetBackgroundTrackingModeCheckboxClicked(isChecked: Boolean) {
        Affise.setBackgroundTrackingEnabled(isChecked)
        _backgroundTrackingModeState.value = Affise.isBackgroundTrackingEnabled()
        output("IsBackgroundTrackingEnabled: ${_backgroundTrackingModeState.value}")
    }

    override fun onSetTrackingModeCheckboxClicked(isChecked: Boolean) {
        Affise.setTrackingEnabled(isChecked)
        _trackingModeState.value = Affise.isTrackingEnabled()
        output("IsTrackingEnabled: ${_trackingModeState.value}")
    }

    override fun clearOutput() {
        _output.postValue("")
    }

    private fun output(data: String) {
        if (_output.value.isNullOrBlank()) {
            _output.postValue(data)
        } else {
            _output.postValue("${_output.value}\n$data")
        }
    }
}