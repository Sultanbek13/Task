package com.sultandev.task.presentation.fragmetns.profile.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultandev.task.data.local.pref.SharedPref
import com.sultandev.task.data.remote.models.userinfo.ProfileData
import com.sultandev.task.domain.repository.impl.AuthRepositoryImpl
import com.sultandev.task.domain.repository.impl.UserInfoRepositoryImpl
import com.sultandev.task.presentation.fragmetns.profile.ProfileViewModel
import com.sultandev.task.utils.hasConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModelImpl @Inject constructor(
    private val userInfoRepository: UserInfoRepositoryImpl,
    private val authRepository: AuthRepositoryImpl,
    private val sharedPref: SharedPref
) :
    ProfileViewModel, ViewModel() {

    override val messageFlow: MutableStateFlow<String> = MutableStateFlow("")

    override val currentUserInfoFlow: MutableSharedFlow<ProfileData> = MutableSharedFlow()

    override fun getCurrentUserInfo() {
        if (hasConnection()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response =
                        userInfoRepository.getCurrentUser(authRepository.getAccessToken())
                    if (response.isSuccessful) {
                        sharedPref.saveUserInfo(response.body()!!.profile_data)
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

    override fun userLocalData() {
        getCurrentUserInfo()
        viewModelScope.launch {
            currentUserInfoFlow.emit(sharedPref.getUserInfo())
        }
    }
}