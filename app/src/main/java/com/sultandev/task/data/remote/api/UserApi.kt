package com.sultandev.task.data.remote.api

import com.sultandev.task.data.remote.models.userinfo.UserInfoData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface UserApi {

    @GET("/api/v1/users/me/")
    suspend fun getCurrentUser(@Header("Authorization") token: String): Response<UserInfoData>

    @PUT("/api/v1/users/me/")
    suspend fun updateCurrentUser(@Header("Authorization") token: String): Response<Response<Unit>>

}