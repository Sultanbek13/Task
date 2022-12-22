package com.sultandev.task.presentation.fragmetns.confirm

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fraggjkee.smsconfirmationview.SmsConfirmationView
import com.google.android.material.slider.Slider
import com.sultandev.task.R
import com.sultandev.task.databinding.FragmentCodeConfirmationBinding
import com.sultandev.task.presentation.fragmetns.confirm.impl.ConfirmViewModelImpl
import com.sultandev.task.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ConfirmFragment: Fragment(R.layout.fragment_code_confirmation) {

    private val binding: FragmentCodeConfirmationBinding by viewBinding()
    private val navArgs: ConfirmFragmentArgs by navArgs()
    private val viewModel: ConfirmViewModel by viewModels<ConfirmViewModelImpl>()
    private var phoneNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        phoneNumber = navArgs.phoneNumber

        viewModel.isUserExits.onEach {
            if(it) findNavController().navigate(ConfirmFragmentDirections.actionConfirmFragmentToMainFragment())
            else findNavController().navigate(ConfirmFragmentDirections.actionConfirmFragmentToRegisterFragment(phoneNumber))
        }.launchIn(lifecycleScope)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvPhoneNumber.text = phoneNumber

        viewModel.messageFlow.onEach {
            toast(requireContext(), it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.btnNext.setOnClickListener {
            val code = binding.smsCodeView.enteredCode
            viewModel.checkCode(phoneNumber, code)
        }
    }
}