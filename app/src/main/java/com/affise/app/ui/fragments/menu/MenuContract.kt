package com.affise.app.ui.fragments.menu

import androidx.lifecycle.LiveData
import com.affise.app.ui.fragments.menu.adapters.Menu

interface MenuContract {
    interface ViewModel {
        val menu: LiveData<List<Menu>>
        val currentMenu: LiveData<Menu.MenuItem>

        fun setCurrentMenu(item: Menu.MenuItem)
    }
}