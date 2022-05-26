package com.affise.app.dependencies.activities.modules

import androidx.lifecycle.ViewModelProvider
import com.affise.app.dependencies.models.DaggerViewModelFactory
import com.affise.app.ui.activity.main.MainActivity
import com.affise.app.ui.activity.main.MainActivityContract
import com.affise.app.ui.activity.main.MainActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideViewModel(
        activity: MainActivity,
        viewModelFactory: DaggerViewModelFactory
    ): MainActivityContract.ViewModel = ViewModelProvider(activity, viewModelFactory)
        .get(MainActivityViewModel::class.java)
}