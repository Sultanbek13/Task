package com.sultandev.task.presentation.fragmetns.main.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultandev.task.data.remote.models.userinfo.ItemChat
import com.sultandev.task.domain.repository.impl.UserInfoRepositoryImpl
import com.sultandev.task.presentation.fragmetns.main.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(private val userInfoRepository: UserInfoRepositoryImpl) :
    MainViewModel, ViewModel() {

    override val listChatsFlow: MutableSharedFlow<List<ItemChat>> = MutableSharedFlow()

    override fun getAllChats() {
        viewModelScope.launch {
            listChatsFlow.emit(userInfoRepository.getListChats())
        }
    }
}