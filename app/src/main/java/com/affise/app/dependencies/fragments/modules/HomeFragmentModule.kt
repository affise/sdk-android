package com.affise.app.dependencies.fragments.modules

import androidx.lifecycle.ViewModelProvider
import com.affise.app.dependencies.models.DaggerViewModelFactory
import com.affise.app.ui.activity.main.MenuHolder
import com.affise.app.ui.fragments.home.HomeContract
import com.affise.app.ui.fragments.home.HomeFragment
import com.affise.app.ui.fragments.home.HomeViewModel
import dagger.Module
import dagger.Provides

@Module
class HomeFragmentModule {

    @Provides
    fun provideMenuHolder(
        fragment: HomeFragment
    ): MenuHolder = fragment.requireActivity() as MenuHolder

    @Provides
    fun provideViewModel(
        fragment: HomeFragment,
        viewModelFactory: DaggerViewModelFactory
    ): HomeContract.ViewModel = ViewModelProvider(
        fragment, viewModelFactory
    ).get(HomeViewModel::class.java)
}