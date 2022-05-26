package com.affise.app.dependencies.fragments.modules

import androidx.lifecycle.ViewModelProvider
import com.affise.app.dependencies.models.DaggerViewModelFactory
import com.affise.app.ui.fragments.details.ProductDetailsContract
import com.affise.app.ui.fragments.details.ProductDetailsFragment
import com.affise.app.ui.fragments.details.ProductDetailsViewModel
import dagger.Module
import dagger.Provides

@Module
class ProductDetailsFragmentModule {

    @Provides
    fun provideViewModel(
        fragment: ProductDetailsFragment,
        viewModelFactory: DaggerViewModelFactory
    ): ProductDetailsContract.ViewModel = ViewModelProvider(
        fragment, viewModelFactory
    ).get(ProductDetailsViewModel::class.java)
}