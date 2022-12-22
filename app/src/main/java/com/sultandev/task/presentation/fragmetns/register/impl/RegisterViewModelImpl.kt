package com.sultandev.task.presentation.fragmetns.register.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultandev.task.data.remote.models.UserRegisterRequestData
import com.sultandev.task.domain.repository.impl.AuthRepositoryImpl
import com.sultandev.task.presentation.fragmetns.register.RegisterViewModel
import com.sultandev.task.utils.hasConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RegisterViewModelImpl @Inject constructor(private val authRepository: AuthRepositoryImpl) :
    RegisterViewModel, ViewModel() {

    override val messageFlow: MutableStateFlow<String> = MutableStateFlow("")

    override val openMainFragmentFlow: MutableSharedFlow<Unit> = MutableSharedFlow()

    override fun openMainFragment() {
        viewModelScope.launch {
            openMainFragmentFlow.emit(Unit)
        }
    }

    override fun userRegister(phoneNumber: String, name: String, username: String) {
        if (hasConnection()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = authRepository.userRegister(
                        UserRegisterRequestData(
                            phoneNumber,
                            name,
                            username
                        )
                    )
                    if(response.code() == 422) messageFlow.emit(response.message())
                    if (response.body() == null) {
                        messageFlow.emit("Response null")
                    }
                    if (response.isSuccessful) {
                        authRepository.setUserRegistered(true)
                        authRepository.saveRefreshToken(response.body()!!.refreshToken)
                        authRepository.saveAccessToken(response.body()!!.accessToken)
                        openMainFragment()
                    } else {
                        messageFlow.emit(response.message())
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
}