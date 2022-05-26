package com.affise.app.ui.fragments.metrics

import androidx.lifecycle.LiveData

class MetricsContract {
    interface ViewModel {
        val enabled: LiveData<Boolean>

        fun setMetricsEnabled(metricsEnabled: Boolean)
    }
}