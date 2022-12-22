package com.sultandev.task.presentation.fragmetns.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface AuthViewModel {

    val sendCodeFlow: SharedFlow<Boolean>

    val openConfirmFlow: SharedFlow<Unit>

    val checkUserRegisteredFlow: SharedFlow<Boolean>

    val messageFlow: Flow<String>

    val phoneNumberFromLocal: SharedFlow<String>

    fun sendCode(phone: String)

    fun checkUserRegistered()

}