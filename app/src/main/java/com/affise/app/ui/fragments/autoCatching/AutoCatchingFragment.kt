package com.affise.app.ui.fragments.autoCatching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.affise.app.R
import com.affise.app.databinding.FragmentAutoCatchingBinding
import com.affise.app.ui.activity.main.MenuHolder
import com.affise.attribution.events.autoCatchingClick.AutoCatchingType
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AutoCatchingFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: AutoCatchingContract.ViewModel

    @Inject
    lateinit var menuHolder: MenuHolder

    lateinit var binding: FragmentAutoCatchingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAutoCatchingBinding.inflate(layoutInflater).apply {
        binding = this

        menuSate.setOnClickListener {
            menuHolder.changeMenuSate()
        }

        changeTypes.setOnClickListener {
            val checkedItems = viewModel.checkedItems.value ?: emptyList()

            val checked = AutoCatchingType.values().map {
                checkedItems.contains(it)
            }.toBooleanArray()

            val items: Array<CharSequence> = AutoCatchingType.values()
                .map { it.name }
                .toTypedArray()

            AlertDialog.Builder(requireContext())
                .setTitle(R.string.select_auto_catching_types_title)
                .setPositiveButton(R.string.ok, null)
                .setMultiChoiceItems(
                    items,
                    checked
                ) { _, which, isChecked ->
                    viewModel.setSelected(which, isChecked)
                }.show()
        }

        testButton.setOnClickListener { }
        testText.setOnClickListener { }
        testImageButton.setOnClickListener { }
        testImage.setOnClickListener { }
        testGroup.setOnClickListener { }
    }.root
}