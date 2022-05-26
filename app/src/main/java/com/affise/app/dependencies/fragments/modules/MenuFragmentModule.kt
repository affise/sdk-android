package com.affise.app.dependencies.fragments.modules

import androidx.lifecycle.ViewModelProvider
import com.affise.app.dependencies.models.DaggerViewModelFactory
import com.affise.app.ui.activity.main.MenuHolder
import com.affise.app.ui.fragments.menu.MenuContract
import com.affise.app.ui.fragments.menu.MenuFragment
import com.affise.app.ui.fragments.menu.MenuViewModel
import dagger.Module
import dagger.Provides

@Module
class MenuFragmentModule {

    @Provides
    fun provideMenuHolder(
        fragment: MenuFragment
    ): MenuHolder = fragment.requireActivity() as MenuHolder

    @Provides
    fun provideViewModel(
        fragment: MenuFragment,
        viewModelFactory: DaggerViewModelFactory
    ): MenuContract.ViewModel = ViewModelProvider(
        fragment, viewModelFactory
    ).get(MenuViewModel::class.java)
}