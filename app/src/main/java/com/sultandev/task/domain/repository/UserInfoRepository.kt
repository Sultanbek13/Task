package com.sultandev.task.domain.repository

import com.sultandev.task.data.remote.models.userinfo.*
import retrofit2.Response

interface UserInfoRepository {

    suspend fun getCurrentUser(accessToken: String): Response<UserInfoData>

    suspend fun getListChats(): List<ItemChat>

    suspend fun getNewAccessToken(refreshToken: String)

    suspend fun getAvatarUri(): String

    suspend fun setAvatarUri(uri: String)

    suspend fun updateCurrentUser(updateData: UserUpdateData): Response<Avatars>

    suspend fun saveUserInfo(userInfo: ProfileData)

    suspend fun getUserInfo(): ProfileData

    suspend fun getUpdateUserInfo(): Boolean

    suspend fun setUpdateUserInfo(state: Boolean)

}