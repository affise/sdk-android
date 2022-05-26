package com.affise.app.dependencies.fragments.modules

import androidx.lifecycle.ViewModelProvider
import com.affise.app.dependencies.models.DaggerViewModelFactory
import com.affise.app.ui.activity.main.MenuHolder
import com.affise.app.ui.fragments.likes.LikesContract
import com.affise.app.ui.fragments.likes.LikesFragment
import com.affise.app.ui.fragments.likes.LikesViewModel
import dagger.Module
import dagger.Provides

@Module
class LikesFragmentModule {

    @Provides
    fun provideMenuHolder(
        fragment: LikesFragment
    ): MenuHolder = fragment.requireActivity() as MenuHolder

    @Provides
    fun provideViewModel(
        fragment: LikesFragment,
        viewModelFactory: DaggerViewModelFactory
    ): LikesContract.ViewModel = ViewModelProvider(
        fragment, viewModelFactory
    ).get(LikesViewModel::class.java)
}