package com.affise.app.dependencies.store

import com.affise.app.storage.FakeStorageImpl
import com.affise.app.storage.Storage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StoreModule {

    @Singleton
    @Provides
    fun productRepository(): Storage = FakeStorageImpl()
}