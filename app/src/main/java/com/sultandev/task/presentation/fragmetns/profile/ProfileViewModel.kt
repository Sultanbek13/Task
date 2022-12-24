package com.sultandev.task.presentation.fragmetns.profile

import android.net.Uri
import com.sultandev.task.data.remote.models.userinfo.Avatar
import com.sultandev.task.data.remote.models.userinfo.ProfileData
import com.sultandev.task.data.remote.models.userinfo.UserUpdateData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface ProfileViewModel {

    val messageFlow: Flow<String>

    val currentUserInfoFlow: SharedFlow<ProfileData>

    val changeAvatarFlow: SharedFlow<String>

    fun getCurrentUserInfo()

    fun userLocalData()

    fun setNewAvatarUri(uri: String)

    fun getAvatarUri()

    fun updateCurrentUser(profileData: ProfileData)

}