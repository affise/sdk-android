package com.affise.app.dependencies

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.affise.app.dependencies.activities.ActivityModule
import com.affise.app.dependencies.fragments.FragmentModule
import com.affise.app.dependencies.models.ViewModelModule
import com.affise.app.dependencies.repositories.RepositoryModule
import com.affise.app.dependencies.store.StoreModule
import com.affise.app.dependencies.usecases.UseCaseModule
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module(
    includes = [
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        UseCaseModule::class,
        StoreModule::class
    ]
)
class AppModule {

    @Provides
    @Singleton
    fun mapper(): ObjectMapper = ObjectMapper()

    @Provides
    @Singleton
    fun sharedPreferences(app: Application): SharedPreferences = app.getSharedPreferences(
        "com.affise.attribution",
        Context.MODE_PRIVATE
    )
}