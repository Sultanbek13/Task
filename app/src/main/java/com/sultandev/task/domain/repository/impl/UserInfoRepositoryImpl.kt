package com.sultandev.task.domain.repository.impl

import com.sultandev.task.data.local.pref.SharedPref
import com.sultandev.task.data.remote.api.AuthApi
import com.sultandev.task.data.remote.api.UserApi
import com.sultandev.task.data.remote.models.UserRefreshData
import com.sultandev.task.data.remote.models.userinfo.*
import com.sultandev.task.domain.repository.UserInfoRepository
import retrofit2.Response
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val authApi: AuthApi,
    private val sharedPref: SharedPref
) : UserInfoRepository {

    override suspend fun getCurrentUser(accessToken: String): Response<UserInfoData> {
        val response = userApi.getCurrentUser("Bearer $accessToken")
        if (response.code() == 401) {
            getNewAccessToken(sharedPref.getRefreshToken())
            return getCurrentUser(sharedPref.getAccessToken())
        }
        return response
    }

    override suspend fun getListChats(): List<ItemChat> {
        val listChats: ArrayList<ItemChat> = arrayListOf()
        listChats.add(ItemChat(0, "Alex", true))
        listChats.add(ItemChat(1, "Roma", true))
        listChats.add(ItemChat(2, "Sasha", false))
        return listChats
    }

    override suspend fun getNewAccessToken(refreshToken: String) {
        val response = authApi.getNewAccessToken(UserRefreshData(refreshToken))
        if (response.isSuccessful) {
            sharedPref.setRefreshToken(response.body()?.refreshToken!!)
            sharedPref.setAccessToken(response.body()?.accessToken!!)
        }
    }

    override suspend fun getAvatarUri(): String = sharedPref.getUriAvatarProfile()

    override suspend fun setAvatarUri(uri: String) = sharedPref.saveUriAvatarProfile(uri)

    override suspend fun updateCurrentUser(updateData: UserUpdateData): Response<Avatars> {
        return userApi.updateCurrentUser(sharedPref.getAccessToken(), updateData)
    }

    override suspend fun saveUserInfo(userInfo: ProfileData) = sharedPref.saveUserInfo(userInfo)

    override suspend fun getUserInfo(): ProfileData = sharedPref.getUserInfo()

    override suspend fun getUpdateUserInfo(): Boolean = sharedPref.getUpdateProfile()

    override suspend fun setUpdateUserInfo(state: Boolean) = sharedPref.setUpdateProfile(state)
}