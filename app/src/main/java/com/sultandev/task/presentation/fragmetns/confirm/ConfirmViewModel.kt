package com.sultandev.task.presentation.fragmetns.confirm

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface ConfirmViewModel {

    val checkCodeFlow: Flow<Unit>

    val messageFlow: Flow<String>

    val isUserExits: SharedFlow<Boolean>

    fun checkCode(phone: String, code: String)

}