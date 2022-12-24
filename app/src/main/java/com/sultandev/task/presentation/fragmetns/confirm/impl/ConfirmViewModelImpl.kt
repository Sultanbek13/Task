package com.sultandev.task.presentation.fragmetns.confirm.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultandev.task.data.remote.models.UserCheckRequestData
import com.sultandev.task.domain.repository.impl.AuthRepositoryImpl
import com.sultandev.task.domain.repository.impl.UserInfoRepositoryImpl
import com.sultandev.task.presentation.fragmetns.confirm.ConfirmViewModel
import com.sultandev.task.utils.hasConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModelImpl @Inject constructor(private val authRepository: AuthRepositoryImpl, private val userInfo: UserInfoRepositoryImpl) :
    ConfirmViewModel, ViewModel() {

    override val checkCodeFlow: MutableStateFlow<Unit> = MutableStateFlow(Unit)

    override val messageFlow: MutableStateFlow<String> = MutableStateFlow("")

    override val isUserExits: MutableSharedFlow<Boolean> = MutableSharedFlow()

    override fun checkCode(phone: String, code: String) {
        if (hasConnection()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    if (code == authRepository.correctCode()) {
                        val response = authRepository.checkAuthCode(UserCheckRequestData(phone, code))
                        if(response.code() == 422) {
                            messageFlow.emit(response.message())
                        }
                        if (response.isSuccessful) {
                            if(response.body()?.isUserExists == false) {
                                userInfo.setUpdateUserInfo(true)
                                isUserExits.emit(false)
                            } else {
                                authRepository.setUserRegistered(true)
                                authRepository.savePhoneNumber(phone)
                                authRepository.saveRefreshToken(response.body()!!.refreshToken)
                                authRepository.saveAccessToken(response.body()!!.accessToken)
                                checkCodeFlow.emit(Unit)
                                userInfo.setUpdateUserInfo(true)
                                isUserExits.emit(true)
                            }
                        } else {
                            messageFlow.emit(response.errorBody()!!.string())
                        }
                    } else {
                        messageFlow.emit("Wrong code :(")
                    }
                } catch (e: IOException) {
                    messageFlow.emit(e.message!!)
                }
            }
        } else {
            viewModelScope.launch {
                messageFlow.emit("No internet connection")
            }
        }
    }
}