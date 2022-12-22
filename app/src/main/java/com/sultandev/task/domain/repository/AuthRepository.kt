package com.sultandev.task.domain.repository

import com.sultandev.task.data.remote.models.*
import retrofit2.Response

interface AuthRepository {

    suspend fun sendAuthCode(userPhoneRequestData: UserPhoneRequestData): Response<Unit>

    suspend fun checkAuthCode(userCheckRequestData: UserCheckRequestData): Response<UserCheckResponseData>

    suspend fun userRegister(userRegisterRequestData: UserRegisterRequestData): Response<UserResponseData>

    fun saveRefreshToken(refreshToken: String)

    fun saveAccessToken(accessToken: String)

    fun getAccessToken(): String

    fun savePhoneNumber(phoneNumber: String)

    fun getPhoneNumber(): String

    fun setUserRegistered(state: Boolean)

    fun getUserRegistered(): Boolean

    suspend fun correctCode(): String

}