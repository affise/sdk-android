package com.affise.app.dependencies.fragments.modules

import androidx.lifecycle.ViewModelProvider
import com.affise.app.dependencies.models.DaggerViewModelFactory
import com.affise.app.ui.activity.main.MenuHolder
import com.affise.app.ui.fragments.cart.CartContract
import com.affise.app.ui.fragments.cart.CartFragment
import com.affise.app.ui.fragments.cart.CartViewModel
import dagger.Module
import dagger.Provides

@Module
class CartFragmentModule {

    @Provides
    fun provideMenuHolder(
        fragment: CartFragment
    ): MenuHolder = fragment.requireActivity() as MenuHolder

    @Provides
    fun provideViewModel(
        fragment: CartFragment,
        viewModelFactory: DaggerViewModelFactory
    ): CartContract.ViewModel = ViewModelProvider(
        fragment, viewModelFactory
    ).get(CartViewModel::class.java)
}