package com.sultandev.task.presentation.fragmetns.main.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sultandev.task.R
import com.sultandev.task.databinding.FragmentChatDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatDetailFragment: Fragment(R.layout.fragment_chat_detail) {

    private val args: ChatDetailFragmentArgs by navArgs()
    private val binding: FragmentChatDetailBinding by viewBinding()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = args.chatCurrentUser

        binding.apply {
            tvUserName.text = data.name
            if(data.status) {
                userStatus.text = "Online"
                userStatus.setTextColor(Color.parseColor("#5ECF56"))
            } else {
                userStatus.text = "Offline"
                userStatus.setTextColor(Color.parseColor("#C12E2E"))
            }

            iconBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}