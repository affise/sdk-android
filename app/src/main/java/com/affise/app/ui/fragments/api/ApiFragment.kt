package com.affise.app.ui.fragments.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.affise.app.databinding.FragmentMainApiBinding
import com.affise.app.extensions.hideKeyboard
import com.affise.app.ui.activity.main.MenuHolder
import com.affise.attribution.referrer.ReferrerKey
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ApiFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: ApiContract.ViewModel

    @Inject
    lateinit var menuHolder: MenuHolder

    lateinit var binding: FragmentMainApiBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainApiBinding.inflate(layoutInflater).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.menuSate.setOnClickListener {
            menuHolder.changeMenuSate()
        }

        viewModel.output.observe(viewLifecycleOwner, binding.outputValue::setText)

        binding.output.setEndIconOnClickListener {
            viewModel.clearOutput()
        }

        binding.debugValidate.setOnClickListener {
            viewModel.onDebugValidateClicked()
        }

        binding.registerDeeplink.setOnClickListener {
            viewModel.onRegisterDeeplinkClicked()
        }

        binding.getReferrer.setOnClickListener {
            viewModel.onGetReferrerClicked()
        }

        binding.getReferrerValue.setOnClickListener {
            viewModel.onGetReferrerValueClicked(ReferrerKey.AD_ID)
        }

        binding.status.setOnClickListener {
            viewModel.onStatusClicked()
        }

        binding.randomPushToken.setOnClickListener {
            viewModel.onRandomPushTokenClicked()
        }

        binding.randomUserId.setOnClickListener {
            viewModel.onRandomUserIdClicked()
        }

        binding.randomDeviceId.setOnClickListener {
            viewModel.onRandomDeviceIdClicked()
        }

        binding.providers.setOnClickListener {
            viewModel.onProvidersClicked()
        }

        binding.setOfflineModeSwitch.apply {
            viewModel.offlineModeState.observe(viewLifecycleOwner, ::setChecked)
            setOnClickListener {
                viewModel.onSetOfflineModeCheckboxClicked(isChecked)
            }
        }

        binding.setBackgroundTrackingModeSwitch.apply {
            viewModel.backgroundTrackingModeState.observe(viewLifecycleOwner, ::setChecked)
            setOnClickListener {
                viewModel.onSetBackgroundTrackingModeCheckboxClicked(isChecked)
            }
        }

        binding.setTrackingModeSwitch.apply {
            viewModel.trackingModeState.observe(viewLifecycleOwner, ::setChecked)
            setOnClickListener {
                viewModel.onSetTrackingModeCheckboxClicked(isChecked)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        requireActivity().hideKeyboard()
    }
}