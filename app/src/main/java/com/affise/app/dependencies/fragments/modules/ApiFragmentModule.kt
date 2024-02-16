package com.affise.app.dependencies.fragments.modules

import androidx.lifecycle.ViewModelProvider
import com.affise.app.dependencies.models.DaggerViewModelFactory
import com.affise.app.ui.activity.main.MenuHolder
import com.affise.app.ui.fragments.api.ApiContract
import com.affise.app.ui.fragments.api.ApiFragment
import com.affise.app.ui.fragments.api.ApiViewModel
import dagger.Module
import dagger.Provides

@Module
class ApiFragmentModule {

    @Provides
    fun provideMenuHolder(
        fragment: ApiFragment
    ): MenuHolder = fragment.requireActivity() as MenuHolder

    @Provides
    fun provideViewModel(
        fragment: ApiFragment,
        viewModelFactory: DaggerViewModelFactory
    ): ApiContract.ViewModel = ViewModelProvider(
        fragment, viewModelFactory
    ).get(ApiViewModel::class.java)
}