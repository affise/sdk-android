package com.affise.app.ui.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.affise.app.databinding.FragmentMainMenuBinding
import com.affise.app.ui.activity.main.MenuHolder
import com.affise.app.ui.fragments.menu.adapters.AdapterMenu
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MenuFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: MenuContract.ViewModel

    @Inject
    lateinit var menuHolder: MenuHolder

    @Inject
    lateinit var adapterMenu: AdapterMenu

    lateinit var binding: FragmentMainMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainMenuBinding.inflate(layoutInflater).apply {
        binding = this

        menu.adapter = adapterMenu.apply {
            onClick = viewModel::setCurrentMenu
        }
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.menu.observe(viewLifecycleOwner) {
            adapterMenu.submitList(it)
        }

        viewModel.currentMenu.observe(viewLifecycleOwner) {
            adapterMenu.current = it

            menuHolder.selectMenu(it)
        }
    }
}