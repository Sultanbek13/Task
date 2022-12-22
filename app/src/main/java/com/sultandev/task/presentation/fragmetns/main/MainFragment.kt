package com.sultandev.task.presentation.fragmetns.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sultandev.task.R
import com.sultandev.task.databinding.FragmentMainBinding
import com.sultandev.task.presentation.fragmetns.main.impl.MainViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class MainFragment: Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { ChatsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvListChat.adapter = adapter

        binding.iconProfile.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToProfileFragment())
        }

        viewModel.listChatsFlow.onEach {
            adapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.getAllChats()

        adapter.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToChatDetailFragment(it))
        }
    }
}