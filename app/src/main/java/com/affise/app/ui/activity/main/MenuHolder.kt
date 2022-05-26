package com.affise.app.ui.activity.main

import com.affise.app.ui.fragments.menu.adapters.Menu

interface MenuHolder {

    fun changeMenuSate()

    fun selectMenu(menuItem: Menu.MenuItem?)
}