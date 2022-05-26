package com.affise.app.dependencies.fragments.modules

import androidx.lifecycle.ViewModelProvider
import com.affise.app.dependencies.models.DaggerViewModelFactory
import com.affise.app.ui.activity.main.MenuHolder
import com.affise.app.ui.fragments.settings.SettingsContract
import com.affise.app.ui.fragments.settings.SettingsFragment
import com.affise.app.ui.fragments.settings.SettingsViewModel
import dagger.Module
import dagger.Provides

@Module
class SettingsFragmentModule {

    @Provides
    fun provideMenuHolder(
        fragment: SettingsFragment
    ): MenuHolder = fragment.requireActivity() as MenuHolder

    @Provides
    fun provideViewModel(
        fragment: SettingsFragment,
        viewModelFactory: DaggerViewModelFactory
    ): SettingsContract.ViewModel = ViewModelProvider(
        fragment, viewModelFactory
    ).get(SettingsViewModel::class.java)
}