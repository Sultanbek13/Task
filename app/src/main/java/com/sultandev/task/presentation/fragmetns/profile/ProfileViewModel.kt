package com.sultandev.task.presentation.fragmetns.profile

import com.sultandev.task.data.remote.models.userinfo.ProfileData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface ProfileViewModel {

    val messageFlow: Flow<String>

    val currentUserInfoFlow: SharedFlow<ProfileData>

    fun getCurrentUserInfo()

    fun userLocalData()

}