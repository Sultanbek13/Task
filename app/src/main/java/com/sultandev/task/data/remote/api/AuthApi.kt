package com.sultandev.task.data.remote.api

import com.sultandev.task.data.remote.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(@Body userPhoneRequestData: UserPhoneRequestData): Response<Unit>

    @POST("/api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(@Body userCheckRequestData: UserCheckRequestData): Response<UserCheckResponseData>

    @POST("/api/v1/users/register/")
    suspend fun userRegister(@Body userRegisterRequestData: UserRegisterRequestData): Response<UserResponseData>

    @POST("/api/v1/users/refresh-token/")
    suspend fun getNewAccessToken(@Body refreshToken: UserRefreshData): Response<UserCheckResponseData>

}