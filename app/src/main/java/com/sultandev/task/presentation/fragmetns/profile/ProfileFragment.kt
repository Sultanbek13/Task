package com.sultandev.task.presentation.fragmetns.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.sultandev.task.R
import com.sultandev.task.data.remote.models.userinfo.Avatars
import com.sultandev.task.data.remote.models.userinfo.ProfileData
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

    private var pathImage: String = ""

    private lateinit var profileData: ProfileData

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.changeAvatarFlow.onEach {
            pathImage = it
            val uri = Uri.parse(it)
            binding.avatarProfile.setImageURI(uri)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.messageFlow.onEach {
            toast(requireContext(), it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.currentUserInfoFlow.onEach {
            profileData = it
            binding.apply {
                tvPhoneNumber.text = it.phone
                etName.setText(it.name)
                etUserName.setText(it.username)
                etBirthday.setText(it.birthday)
                etCity.setText(it.city)
                etVk.setText(it.vk)
                etInstagram.setText(it.instagram)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.getCurrentUserInfo()
        viewModel.getAvatarUri()

        binding.avatarProfile.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .galleryOnly()
                .start()
        }

        binding.btnCancel.setOnClickListener {
            viewModel.userLocalData()
        }

        binding.apply {
            btnSave.setOnClickListener {
                viewModel.updateCurrentUser(
                    ProfileData(
                        pathImage,
                        Avatars("", "", ""),
                        etBirthday.text.toString(),
                        etCity.text.toString(),
                        profileData.completed_task,
                        profileData.created,
                        profileData.id,
                        etInstagram.text.toString(),
                        "",
                        etName.text.toString(),
                        profileData.online,
                        tvPhoneNumber.text.toString(),
                        "",
                        etUserName.text.toString(),
                        etVk.text.toString()
                        )
                )
                viewModel.getCurrentUserInfo()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val uri: Uri = data?.data!!
                viewModel.setNewAvatarUri(uri.toString())
                binding.avatarProfile.setImageURI(uri)
            }
            ImagePicker.RESULT_ERROR -> {
                toast(requireContext(), ImagePicker.getError(data))
            }
            else -> {
                toast(requireContext(), "Task Cancelled")
            }
        }
    }
}