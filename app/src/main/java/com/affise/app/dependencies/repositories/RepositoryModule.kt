package com.affise.app.dependencies.repositories

import com.affise.app.repositories.ProductRepository
import com.affise.app.repositories.ProductRepositoryImpl
import com.affise.app.storage.Storage
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class RepositoryModule {

    @Reusable
    @Provides
    fun productRepository(
        storage: Storage
    ): ProductRepository = ProductRepositoryImpl(storage)
}