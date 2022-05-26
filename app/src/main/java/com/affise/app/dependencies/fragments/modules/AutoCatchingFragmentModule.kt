package com.affise.app.dependencies.fragments.modules

import androidx.lifecycle.ViewModelProvider
import com.affise.app.dependencies.models.DaggerViewModelFactory
import com.affise.app.ui.activity.main.MenuHolder
import com.affise.app.ui.fragments.autoCatching.AutoCatchingContract
import com.affise.app.ui.fragments.autoCatching.AutoCatchingFragment
import com.affise.app.ui.fragments.autoCatching.AutoCatchingViewModel
import dagger.Module
import dagger.Provides

@Module
class AutoCatchingFragmentModule {

    @Provides
    fun provideMenuHolder(
        fragment: AutoCatchingFragment
    ): MenuHolder = fragment.requireActivity() as MenuHolder

    @Provides
    fun provideViewModel(
        fragment: AutoCatchingFragment,
        viewModelFactory: DaggerViewModelFactory
    ): AutoCatchingContract.ViewModel = ViewModelProvider(
        fragment, viewModelFactory
    ).get(AutoCatchingViewModel::class.java)
}