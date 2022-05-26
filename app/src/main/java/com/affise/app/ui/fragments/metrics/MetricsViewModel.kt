package com.affise.app.ui.fragments.metrics

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.affise.app.application.App
import com.affise.attribution.Affise
import javax.inject.Inject

class MetricsViewModel @Inject constructor(
    private val preferences: SharedPreferences
) : ViewModel(), MetricsContract.ViewModel {

    override val enabled = MutableLiveData(preferences.getBoolean(App.ENABLED_METRICS_KEY, false))

    override fun setMetricsEnabled(metricsEnabled: Boolean) {
        enabled.value?.let {
            if (it != metricsEnabled) {
                preferences.edit()
                    .putBoolean(App.ENABLED_METRICS_KEY, metricsEnabled)
                    .apply()

                Affise.setEnabledMetrics(metricsEnabled)

                enabled.value = metricsEnabled
            }
        }
    }
}