package com.affise.app.ui.activity.main

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.affise.app.R
import com.affise.app.databinding.ActivityMainBinding
import com.affise.app.ui.fragments.menu.adapters.Menu
import com.affise.attribution.Affise
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MenuHolder {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Affise.registerDeeplinkCallback {
            AlertDialog.Builder(this)
                .setMessage(it.toString())
                .create().show()
            true
        }

        with(ActivityMainBinding.inflate(layoutInflater)) {
            setContentView(root)

            binding = this
        }

        Navigation.findNavController(this, R.id.general_menu_holder).apply {
            graph = navInflater.inflate(R.navigation.menu)
        }
    }

    override fun changeMenuSate() {
        binding.generalMenuNavigation.let {
            if (it.isShown) {
                binding.generalDrawer.closeDrawers()
            } else {
                binding.generalDrawer.openDrawer(it)
            }
        }
    }

    override fun selectMenu(menuItem: Menu.MenuItem?) {
        menuItem ?: return

        binding.generalMenuNavigation.let {
            if (it.isShown) {
                binding.generalDrawer.closeDrawers()
            }
        }

        Navigation.findNavController(findViewById(R.id.general_holder)).apply {
            graph = navInflater.inflate(menuItem.graph)
        }
    }

    override fun onBackPressed() {
        binding.generalMenuNavigation.let {
            if (it.isShown) {
                binding.generalDrawer.closeDrawers()

                return
            }
        }

        super.onBackPressed()
    }
}