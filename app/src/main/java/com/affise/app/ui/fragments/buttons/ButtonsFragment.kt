package com.affise.app.ui.fragments.buttons

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import com.affise.app.R
import com.affise.app.databinding.FragmentMainButtonsBinding
import com.affise.app.extensions.hideKeyboard
import com.affise.app.ui.activity.main.MenuHolder
import com.affise.app.ui.fragments.buttons.adapters.EventsAdapter
import com.affise.app.ui.fragments.buttons.adapters.ItemCallback
import com.affise.app.ui.fragments.buttons.factories.DefaultEventsFactory
import com.affise.attribution.Affise
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ButtonsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: ButtonsContract.ViewModel

    @Inject
    lateinit var menuHolder: MenuHolder

    lateinit var binding: FragmentMainButtonsBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainButtonsBinding.inflate(layoutInflater).apply {
        binding = this

        menuSate.setOnClickListener {
            menuHolder.changeMenuSate()
        }

        with(binding.webView) {
            settings.javaScriptEnabled = true
            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()
        }

        tabEvents.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab ?: return
                val tabText = tab.text
                binding.eventsRecyclerView.isVisible = tabText == resources.getString(R.string.events)
                binding.webView.isVisible = tabText == resources.getString(R.string.web_events)
                binding.billingView.isVisible = tabText == resources.getString(R.string.store)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.sendEventsButton.setOnClickListener {
            DefaultEventsFactory().createEvents().forEach {
                it.send()
            }
        }

        binding.eventsRecyclerView.adapter = EventsAdapter(ItemCallback()) {
            it.send()
        }.apply {
            submitList(DefaultEventsFactory().createEvents())
        }
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Affise.registerWebView(binding.webView)

        try {
            binding.webView.loadUrl("file:///android_asset/index.html")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()

        requireActivity().hideKeyboard()
    }

    override fun onDestroyView() {
        Affise.unregisterWebView()

        super.onDestroyView()
    }
}