package com.affise.app.ui.fragments.metrics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.affise.app.databinding.FragmentMainMetricsBinding
import com.affise.app.ui.activity.main.MenuHolder
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MetricsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: MetricsContract.ViewModel

    @Inject
    lateinit var menuHolder: MenuHolder

    lateinit var binding: FragmentMainMetricsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainMetricsBinding.inflate(layoutInflater).apply {
        binding = this

        menuSate.setOnClickListener {
            menuHolder.changeMenuSate()
        }
        viewModel.enabled.observe(viewLifecycleOwner) {
            metrics.isChecked = it
        }

        metrics.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setMetricsEnabled(isChecked)
        }
    }.root
}