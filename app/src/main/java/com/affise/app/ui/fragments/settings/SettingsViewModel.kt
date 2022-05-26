package com.affise.app.ui.fragments.settings

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.affise.app.application.App
import com.affise.attribution.Affise
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import java.lang.IllegalStateException
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val preferences: SharedPreferences
) : ViewModel(), SettingsContract.ViewModel {
    private val _offlineModeState = MutableLiveData<Boolean>()
    private val _backgroundTrackingModeState = MutableLiveData<Boolean>()
    private val _trackingModeState = MutableLiveData<Boolean>()
    private val _pushToken = MutableLiveData<String>()

    override val offlineModeState: LiveData<Boolean>
        get() = _offlineModeState

    override val backgroundTrackingModeState: LiveData<Boolean>
        get() = _backgroundTrackingModeState

    override val trackingModeState: LiveData<Boolean>
        get() = _trackingModeState

    override val pushToken: LiveData<String>
        get() = _pushToken

    init {
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

    override fun setSecretId(secretId: String) {
        preferences.edit()
            .putString(App.SECRET_ID_KEY, secretId)
            .apply()

        Affise.setSecretId(secretId)
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