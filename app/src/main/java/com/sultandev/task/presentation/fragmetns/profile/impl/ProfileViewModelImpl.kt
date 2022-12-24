package com.sultandev.task.presentation.fragmetns.profile.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultandev.task.data.remote.models.userinfo.ProfileData
import com.sultandev.task.data.remote.models.userinfo.UserUpdateData
import com.sultandev.task.domain.repository.impl.AuthRepositoryImpl
import com.sultandev.task.domain.repository.impl.UserInfoRepositoryImpl
import com.sultandev.task.presentation.fragmetns.profile.ProfileViewModel
import com.sultandev.task.utils.hasConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModelImpl @Inject constructor(
    private val userInfoRepository: UserInfoRepositoryImpl,
    private val authRepository: AuthRepositoryImpl,
) :
    ProfileViewModel, ViewModel() {

    override val messageFlow: MutableStateFlow<String> = MutableStateFlow("")

    override val currentUserInfoFlow: MutableSharedFlow<ProfileData> = MutableSharedFlow()

    override val changeAvatarFlow: MutableSharedFlow<String> = MutableSharedFlow()

    override fun getCurrentUserInfo() {
        if (hasConnection()) {
            viewModelScope.launch(Dispatchers.IO) {
                if (userInfoRepository.getUpdateUserInfo()) {
                    try {
                        val response =
                            userInfoRepository.getCurrentUser(authRepository.getAccessToken())
                        if (response.isSuccessful) {
                            userInfoRepository.saveUserInfo(response.body()!!.profile_data)
                            userInfoRepository.setUpdateUserInfo(false)
                            currentUserInfoFlow.emit(response.body()!!.profile_data)
                        } else {
                            messageFlow.emit(response.errorBody()!!.string())
                        }
                    } catch (e: IOException) {
                        messageFlow.emit(e.message!!)
                    }
                } else {
                    delay(1000)
                    currentUserInfoFlow.emit(userInfoRepository.getUserInfo())
                }
            }
        } else {
            viewModelScope.launch {
                messageFlow.emit("No internet connection :(")
            }
        }
    }

    override fun userLocalData() {
        viewModelScope.launch {
            currentUserInfoFlow.emit(userInfoRepository.getUserInfo())
        }
    }


    override fun setNewAvatarUri(uri: String) {
        viewModelScope.launch {
            userInfoRepository.setAvatarUri(uri)
        }
    }

    override fun getAvatarUri() {
        viewModelScope.launch {
            changeAvatarFlow.emit(userInfoRepository.getAvatarUri())
        }
    }

    override fun updateCurrentUser(profileData: ProfileData) {
        viewModelScope.launch(Dispatchers.IO) {
            userInfoRepository.saveUserInfo(profileData)
            try {
                val response = userInfoRepository.updateCurrentUser(
                    UserUpdateData(
                        profileData.avatar,
                        profileData.birthday,
                        profileData.city,
                        profileData.instagram,
                        profileData.name,
                        profileData.status,
                        profileData.username,
                        profileData.vk
                    )
                )
                if (response.isSuccessful) {
                    userInfoRepository.setUpdateUserInfo(true)
                    messageFlow.emit("Successfully updated")
                }
            } catch (e: IOException) {
                messageFlow.emit(e.message!!)
            }
        }
    }
}