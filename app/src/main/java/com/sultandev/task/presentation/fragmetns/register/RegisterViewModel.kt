package com.sultandev.task.presentation.fragmetns.register

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface RegisterViewModel {

    val messageFlow: Flow<String>

    val openMainFragmentFlow: SharedFlow<Unit>

    fun openMainFragment()

    fun userRegister(phoneNumber: String, name: String, username: String)

}