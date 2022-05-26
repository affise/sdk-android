package com.affise.app.dependencies.activities

import com.affise.app.dependencies.activities.modules.MainActivityModule
import com.affise.app.dependencies.scope.ActivityScope
import com.affise.app.ui.activity.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivity(): MainActivity
}