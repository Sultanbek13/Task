package com.sultandev.task.presentation.fragmetns.auth.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultandev.task.data.remote.models.UserPhoneRequestData
import com.sultandev.task.domain.repository.impl.AuthRepositoryImpl
import com.sultandev.task.presentation.fragmetns.auth.AuthViewModel
import com.sultandev.task.utils.hasConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AuthViewModelImpl @Inject constructor(private val authRepository: AuthRepositoryImpl) :
    AuthViewModel, ViewModel() {

    override val sendCodeFlow: MutableSharedFlow<Boolean> = MutableSharedFlow()
    override val openConfirmFlow: MutableSharedFlow<Unit> = MutableSharedFlow()
    override val checkUserRegisteredFlow: MutableSharedFlow<Boolean> = MutableSharedFlow()
    override val messageFlow: MutableStateFlow<String> = MutableStateFlow("")
    override val phoneNumberFromLocal: MutableSharedFlow<String> = MutableSharedFlow()

    override fun sendCode(phone: String) {
        if (hasConnection()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = authRepository.sendAuthCode(UserPhoneRequestData(phone))
                    if (response.code() == 422) {
                        messageFlow.emit(response.message())
                    }
                    if (response.isSuccessful) {
                        openConfirmFlow.emit(Unit)
                        messageFlow.emit("Send code")
                    } else {
                        messageFlow.emit(response.errorBody()!!.string())
                    }
                } catch (e: IOException) {
                    messageFlow.emit(e.message!!)
                }
            }
        } else {
            viewModelScope.launch {
                messageFlow.emit("No internet connection :(")
            }
        }
    }

    override fun checkUserRegistered() {
        viewModelScope.launch {
            if (authRepository.getUserRegistered()) {
                phoneNumberFromLocal.emit(authRepository.getPhoneNumber())
                checkUserRegisteredFlow.emit(true)
            } else checkUserRegisteredFlow.emit(false)
        }
    }
}