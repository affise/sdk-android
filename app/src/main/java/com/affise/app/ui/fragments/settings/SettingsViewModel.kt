package com.affise.app.ui.fragments.settings

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.affise.app.application.App
import com.affise.attribution.Affise
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val preferences: SharedPreferences
) : ViewModel(), SettingsContract.ViewModel {
    private val _debugModeState = MutableLiveData<Boolean>()
    private val _debugLogRequestState = MutableLiveData<Boolean>()
    private val _debugLogResponseState = MutableLiveData<Boolean>()
    private val _offlineModeState = MutableLiveData<Boolean>()
    private val _backgroundTrackingModeState = MutableLiveData<Boolean>()
    private val _trackingModeState = MutableLiveData<Boolean>()
    private val _pushToken = MutableLiveData<String>()
    private val _affiseAppId = MutableLiveData<String>()
    private val _secretKey = MutableLiveData<String>()
    private val _domain = MutableLiveData<String>()

    override val debugLogRequestState: LiveData<Boolean>
        get() = _debugLogRequestState

    override val debugLogResponseState: LiveData<Boolean>
        get() = _debugLogResponseState

    override val debugModeState: LiveData<Boolean>
        get() = _debugModeState

    override val offlineModeState: LiveData<Boolean>
        get() = _offlineModeState

    override val backgroundTrackingModeState: LiveData<Boolean>
        get() = _backgroundTrackingModeState

    override val trackingModeState: LiveData<Boolean>
        get() = _trackingModeState

    override val pushToken: LiveData<String>
        get() = _pushToken

    override val affiseAppId: LiveData<String>
        get() = _affiseAppId

    override val secretKey: LiveData<String>
        get() = _secretKey
    override val domain: LiveData<String>
        get() = _domain

    init {
        _debugModeState.value = !preferences.getBoolean(App.PRODUCTION_KEY, false)
        _debugLogRequestState.value = preferences.getBoolean(App.DEBUG_REQUEST_KEY, false)
        _debugLogResponseState.value = preferences.getBoolean(App.DEBUG_RESPONSE_KEY, false)
        _affiseAppId.value = preferences.getString(App.AFFISE_APP_ID_KEY, null)
        _secretKey.value = preferences.getString(App.SECRET_ID_KEY, null)
        _domain.value = preferences.getString(App.DOMAIN_KEY, null)

        _offlineModeState.value = Affise.isOfflineModeEnabled()
        _backgroundTrackingModeState.value = Affise.isBackgroundTrackingEnabled()
        _trackingModeState.value = Affise.isTrackingEnabled()

        FirebaseMessaging.getInstance().token.apply {
            addOnSuccessListener {
                _pushToken.postValue(it?.toString())
            }
            addOnFailureListener {
                _pushToken.postValue("Failed to retrieve token: " + it.message)
            }
        }
    }

    override fun setDebug(enabled: Boolean) {
        preferences.edit()
            .putBoolean(App.PRODUCTION_KEY, !enabled)
            .apply()
    }

    override fun debugLogRequest(show: Boolean) {
        preferences.edit()
            .putBoolean(App.DEBUG_REQUEST_KEY, show)
            .apply()
        App.debugRequest = show
    }

    override fun debugLogResponse(show: Boolean) {
        preferences.edit()
            .putBoolean(App.DEBUG_RESPONSE_KEY, show)
            .apply()
        App.debugResponse = show
    }

    override fun setDomain(domain: String) {
        val value: String? = domain.ifBlank {
            null
        }
        preferences.edit()
            .putString(App.DOMAIN_KEY, value)
            .apply()
    }

    override fun setAffiseAppId(affiseAppId: String) {
        val value: String? = affiseAppId.ifBlank {
            null
        }
        preferences.edit()
            .putString(App.AFFISE_APP_ID_KEY, value)
            .apply()
    }

    override fun setSecretKey(secretKey: String) {
        val value: String? = secretKey.ifBlank {
            null
        }
        preferences.edit()
            .putString(App.SECRET_ID_KEY, value)
            .apply()

        Affise.setSecretId(secretKey)
    }

    override fun onSetOfflineModeCheckboxClicked(isChecked: Boolean) {
        Affise.setOfflineModeEnabled(isChecked)
        _offlineModeState.value = Affise.isOfflineModeEnabled()
    }

    override fun onSetBackgroundTrackingModeCheckboxClicked(isChecked: Boolean) {
        Affise.setBackgroundTrackingEnabled(isChecked)
        _backgroundTrackingModeState.value = Affise.isBackgroundTrackingEnabled()
    }

    override fun onSetTrackingModeCheckboxClicked(isChecked: Boolean) {
        Affise.setTrackingEnabled(isChecked)
        _trackingModeState.value = Affise.isTrackingEnabled()
    }

    override fun onGDPRForgetButtonClicked() {
        Affise.forget("Demo App forget event")
    }

    override fun onCrashApplicationAffiseButtonClicked() {
        Affise.crashApplication()
    }

    override fun onCrashApplicationButtonClicked() {
        throw IllegalStateException("Crash caused by demo app")
    }
}