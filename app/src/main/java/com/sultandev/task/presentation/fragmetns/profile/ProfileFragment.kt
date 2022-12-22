package com.sultandev.task.presentation.fragmetns.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sultandev.task.R
import com.sultandev.task.databinding.FragmentProfileBinding
import com.sultandev.task.presentation.fragmetns.profile.impl.ProfileViewModelImpl
import com.sultandev.task.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding: FragmentProfileBinding by viewBinding()

    private val viewModel: ProfileViewModel by viewModels<ProfileViewModelImpl>()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.messageFlow.onEach {
            toast(requireContext(), it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.currentUserInfoFlow.onEach {
            binding.apply {
                tvPhoneNumber.text = "+" + it.phone
                etName.setText(it.name)
                etUserName.setText(it.username)
                etBirthday.setText(it.birthday)
                etCity.setText(it.city)
                etVk.setText(it.vk)
                etInstagram.setText(it.instagram)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.userLocalData()

    }
}