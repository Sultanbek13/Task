package com.sultandev.task.presentation.fragmetns.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sultandev.task.R
import com.sultandev.task.databinding.FragmentAuthBinding
import com.sultandev.task.presentation.fragmetns.auth.impl.AuthViewModelImpl
import com.sultandev.task.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val binding: FragmentAuthBinding by viewBinding()

    private var phoneNumber: String = ""

    private val viewModel: AuthViewModel by viewModels<AuthViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.phoneNumberFromLocal.onEach {
            phoneNumber = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.checkUserRegisteredFlow.onEach {
            if(it) findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToMainFragment())
        }.launchIn(lifecycleScope)

        viewModel.checkUserRegistered()

        binding.apply {
            etRegion.registerCarrierNumberEditText(etPhoneNumber)
        }

        viewModel.openConfirmFlow.onEach {
            binding.apply {
                phoneNumber = etRegion.fullNumberWithPlus
                toast(requireContext(), phoneNumber)
                if (etPhoneNumber.text.isNullOrEmpty()) {
                    etPhoneNumber.error = getString(R.string.fill_the_field)
                } else {
                    findNavController().navigate(
                        AuthFragmentDirections.actionAuthFragmentToConfirmFragment(
                            phoneNumber
                        )
                    )
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.messageFlow.onEach {
            toast(requireContext(), it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.btnCode.setOnClickListener {
            binding.apply {
                phoneNumber = etRegion.fullNumberWithPlus
            }
            viewModel.sendCode(phoneNumber)
        }
    }
}