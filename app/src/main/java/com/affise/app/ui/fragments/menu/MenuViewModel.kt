package com.affise.app.ui.fragments.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.affise.app.R
import com.affise.app.ui.fragments.menu.adapters.Menu
import javax.inject.Inject

class MenuViewModel @Inject constructor() : ViewModel(), MenuContract.ViewModel {

    private val menuItems = listOf(
        Menu.MenuItem(R.drawable.ic_menu_home, R.string.menu_home, R.navigation.main),
        Menu.MenuItem(R.drawable.ic_menu_favorite, R.string.menu_wishlist, R.navigation.like),
        Menu.MenuItem(R.drawable.ic_menu_shopping, R.string.menu_cart, R.navigation.cart),
        Menu.MenuDivider,
        Menu.MenuItem(
            R.drawable.ic_menu_buttons,
            R.string.menu_test_buttons,
            R.navigation.test_buttons
        ),
        Menu.MenuItem(
            R.drawable.ic_menu_auto_cathing,
            R.string.menu_test_auto_cathing,
            R.navigation.test_auto_cathing
        ),
        Menu.MenuItem(R.drawable.ic_menu_metrics, R.string.menu_test_metrics, R.navigation.metrics),
        Menu.MenuDivider,
        Menu.MenuItem(R.drawable.ic_munu_settings, R.string.menu_settings, R.navigation.settings),
    )

    override val menu = MutableLiveData(menuItems)

    override val currentMenu = MutableLiveData(
        menuItems.mapNotNull { it as? Menu.MenuItem }.first()
    )

    override fun setCurrentMenu(item: Menu.MenuItem) {
        currentMenu.postValue(item)
    }
}