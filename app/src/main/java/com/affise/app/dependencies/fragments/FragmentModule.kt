package com.affise.app.dependencies.fragments

import com.affise.app.dependencies.fragments.modules.*
import com.affise.app.dependencies.scope.FragmentScope
import com.affise.app.ui.fragments.autoCatching.AutoCatchingFragment
import com.affise.app.ui.fragments.buttons.ButtonsFragment
import com.affise.app.ui.fragments.cart.CartFragment
import com.affise.app.ui.fragments.details.ProductDetailsFragment
import com.affise.app.ui.fragments.home.HomeFragment
import com.affise.app.ui.fragments.likes.LikesFragment
import com.affise.app.ui.fragments.menu.MenuFragment
import com.affise.app.ui.fragments.metrics.MetricsFragment
import com.affise.app.ui.fragments.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [MenuFragmentModule::class])
    abstract fun menuFragment(): MenuFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun homeFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ProductDetailsFragmentModule::class])
    abstract fun productDetailsFragment(): ProductDetailsFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [LikesFragmentModule::class])
    abstract fun likesFragment(): LikesFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CartFragmentModule::class])
    abstract fun cartFragment(): CartFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ButtonsFragmentModule::class])
    abstract fun buttonsFragment(): ButtonsFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SettingsFragmentModule::class])
    abstract fun settingsFragment(): SettingsFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [AutoCatchingFragmentModule::class])
    abstract fun autoCatchingFragment(): AutoCatchingFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [MetricsFragmentModule::class])
    abstract fun metricsFragment(): MetricsFragment
}