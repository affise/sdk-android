package com.affise.app.ui.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.affise.app.R
import com.affise.app.databinding.FragmentMainSettingsBinding
import com.affise.app.extensions.hideKeyboard
import com.affise.app.ui.activity.main.MenuHolder
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SettingsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: SettingsContract.ViewModel

    @Inject
    lateinit var menuHolder: MenuHolder

    lateinit var binding: FragmentMainSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainSettingsBinding.inflate(layoutInflater).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.menuSate.setOnClickListener {
            menuHolder.changeMenuSate()
        }

        viewModel.affiseAppId.observe(viewLifecycleOwner, binding.affiseAppIdValue::setText)
        viewModel.secretKey.observe(viewLifecycleOwner, binding.secretIdValue::setText)
        viewModel.domain.observe(viewLifecycleOwner, binding.domainValue::setText)

        binding.save.setOnClickListener {
            viewModel.setAffiseAppId(binding.affiseAppIdValue.text.toString())
            viewModel.setSecretKey(binding.secretIdValue.text.toString())
            viewModel.setDomain(binding.domainValue.text.toString())

            AlertDialog.Builder(requireContext())
                .setMessage(R.string.change_complete)
                .setPositiveButton(R.string.ok, null)
                .show()
        }

        binding.setDebugSwitch.apply {
            viewModel.debugModeState.observe(viewLifecycleOwner, ::setChecked)
            setOnClickListener {
                viewModel.setDebug(isChecked)
            }
        }

        binding.logDebugRequestSwitch.apply {
            viewModel.debugLogRequestState.observe(viewLifecycleOwner, ::setChecked)
            setOnClickListener {
                viewModel.debugLogRequest(isChecked)
            }
        }

        binding.logDebugResponseSwitch.apply {
            viewModel.debugLogResponseState.observe(viewLifecycleOwner, ::setChecked)
            setOnClickListener {
                viewModel.debugLogResponse(isChecked)
            }
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

        binding.gdprForgetButton.setOnClickListener {
            viewModel.onGDPRForgetButtonClicked()
        }

        binding.testApplicationCrashAffiseButton.setOnClickListener {
            viewModel.onCrashApplicationAffiseButtonClicked()
        }

        binding.testApplicationCrashButton.setOnClickListener {
            viewModel.onCrashApplicationButtonClicked()
        }

        viewModel.pushToken.observe(
            viewLifecycleOwner,
            binding.pushTokenInputLayoutInputEditText::setText
        )

    }

    override fun onResume() {
        super.onResume()

        requireActivity().hideKeyboard()
    }
}