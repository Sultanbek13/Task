package com.sultandev.task.presentation.fragmetns.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sultandev.task.R
import com.sultandev.task.databinding.FragmentRegisterBinding
import com.sultandev.task.presentation.fragmetns.register.impl.RegisterViewModelImpl
import com.sultandev.task.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding: FragmentRegisterBinding by viewBinding()
    private val navArgs: RegisterFragmentArgs by navArgs()
    private val viewModel: RegisterViewModel by viewModels<RegisterViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val phoneNumber = navArgs.phoneNumber

        binding.etPhoneNumberRegister.text = phoneNumber

        viewModel.messageFlow.onEach {
            toast(requireContext(), it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.openMainFragmentFlow.onEach {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToMainFragment())
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.btnRegister.setOnClickListener {

            var nameUser = ""
            var username = ""
            var success = false

            binding.apply {
                if (etNameRegister.text.isNullOrEmpty()) {
                    success = false
                    etNameRegister.error = getString(R.string.fill_the_field)
                } else {
                    if (etNameRegister.text!!.length in 3..26) {
                        success = true
                        nameUser = etNameRegister.text.toString()
                    } else {
                        success = false
                        etNameRegister.error = getString(R.string.more_letters)
                    }
                }

                if (etUsernameRegister.text.isNullOrEmpty()) {
                    success = false
                    etUsernameRegister.error = getString(R.string.fill_the_field)
                } else {
                    if (etUsernameRegister.text!!.length in 5..26) {
                        success = true
                        username = etUsernameRegister.text.toString()
                    } else {
                        success = false
                        etUsernameRegister.error = getString(R.string.more_letters)
                    }
                }
            }
            if (success) viewModel.userRegister(phoneNumber, nameUser, username)
        }
    }
}