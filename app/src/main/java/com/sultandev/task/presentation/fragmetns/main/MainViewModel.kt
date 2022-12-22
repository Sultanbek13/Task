package com.sultandev.task.presentation.fragmetns.main

import com.sultandev.task.data.remote.models.userinfo.ItemChat
import kotlinx.coroutines.flow.SharedFlow

interface MainViewModel {

    val listChatsFlow: SharedFlow<List<ItemChat>>

    fun getAllChats()

}