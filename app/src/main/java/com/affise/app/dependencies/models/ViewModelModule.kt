package com.affise.app.dependencies.models

import androidx.lifecycle.ViewModel
import com.affise.app.ui.activity.main.MainActivityViewModel
import com.affise.app.ui.fragments.autoCatching.AutoCatchingViewModel
import com.affise.app.ui.fragments.buttons.ButtonsViewModel
import com.affise.app.ui.fragments.cart.CartViewModel
import com.affise.app.ui.fragments.details.ProductDetailsViewModel
import com.affise.app.ui.fragments.home.HomeViewModel
import com.affise.app.ui.fragments.likes.LikesViewModel
import com.affise.app.ui.fragments.menu.MenuViewModel
import com.affise.app.ui.fragments.metrics.MetricsViewModel
import com.affise.app.ui.fragments.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun mainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MenuViewModel::class)
    fun menuViewModel(viewModel: MenuViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun homeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailsViewModel::class)
    fun productDetailsViewModel(viewModel: ProductDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ButtonsViewModel::class)
    fun buttonsViewModel(viewModel: ButtonsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LikesViewModel::class)
    fun likesViewModel(viewModel: LikesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    fun cartViewModel(viewModel: CartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun settingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AutoCatchingViewModel::class)
    fun autoCatchingViewModel(viewModel: AutoCatchingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MetricsViewModel::class)
    fun metricsViewModel(viewModel: MetricsViewModel): ViewModel
}