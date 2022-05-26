package com.affise.app.dependencies.fragments.modules

import androidx.lifecycle.ViewModelProvider
import com.affise.app.dependencies.models.DaggerViewModelFactory
import com.affise.app.ui.activity.main.MenuHolder
import com.affise.app.ui.fragments.metrics.MetricsContract
import com.affise.app.ui.fragments.metrics.MetricsFragment
import com.affise.app.ui.fragments.metrics.MetricsViewModel
import dagger.Module
import dagger.Provides

@Module
class MetricsFragmentModule {

    @Provides
    fun provideMenuHolder(
        fragment: MetricsFragment
    ): MenuHolder = fragment.requireActivity() as MenuHolder

    @Provides
    fun provideViewModel(
        fragment: MetricsFragment,
        viewModelFactory: DaggerViewModelFactory
    ): MetricsContract.ViewModel = ViewModelProvider(
        fragment, viewModelFactory
    ).get(MetricsViewModel::class.java)
}