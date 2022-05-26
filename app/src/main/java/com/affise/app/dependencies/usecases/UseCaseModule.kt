package com.affise.app.dependencies.usecases

import com.affise.app.repositories.ProductRepository
import com.affise.app.usecase.ProductUseCase
import com.affise.app.usecase.ProductUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class UseCaseModule {

    @Reusable
    @Provides
    fun productRepository(
        repository: ProductRepository
    ): ProductUseCase = ProductUseCaseImpl(repository)
}