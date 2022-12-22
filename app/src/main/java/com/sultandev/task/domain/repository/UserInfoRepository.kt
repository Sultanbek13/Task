package com.sultandev.task.domain.repository

import com.sultandev.task.data.remote.models.userinfo.ItemChat
import com.sultandev.task.data.remote.models.userinfo.UserInfoData
import retrofit2.Response

interface UserInfoRepository {

    suspend fun getCurrentUser(accessToken: String): Response<UserInfoData>

    suspend fun getListChats(): List<ItemChat>

    suspend fun getNewAccessToken(refreshToken: String)

}