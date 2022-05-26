package com.affise.app.dependencies.fragments.modules

import androidx.lifecycle.ViewModelProvider
import com.affise.app.dependencies.models.DaggerViewModelFactory
import com.affise.app.ui.activity.main.MenuHolder
import com.affise.app.ui.fragments.buttons.ButtonsContract
import com.affise.app.ui.fragments.buttons.ButtonsFragment
import com.affise.app.ui.fragments.buttons.ButtonsViewModel
import dagger.Module
import dagger.Provides

@Module
class ButtonsFragmentModule {

    @Provides
    fun provideMenuHolder(
        fragment: ButtonsFragment
    ): MenuHolder = fragment.requireActivity() as MenuHolder

    @Provides
    fun provideViewModel(
        fragment: ButtonsFragment,
        viewModelFactory: DaggerViewModelFactory
    ): ButtonsContract.ViewModel = ViewModelProvider(
        fragment, viewModelFactory
    ).get(ButtonsViewModel::class.java)
}