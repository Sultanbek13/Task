package com.sultandev.task.domain.repository.impl

import com.sultandev.task.data.local.pref.SharedPref
import com.sultandev.task.data.remote.api.AuthApi
import com.sultandev.task.data.remote.models.*
import com.sultandev.task.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val api: AuthApi,  private val sharedPref: SharedPref): AuthRepository {

    override suspend fun sendAuthCode(userPhoneRequestData: UserPhoneRequestData): Response<Unit> = api.sendAuthCode(userPhoneRequestData)

    override suspend fun checkAuthCode(userCheckRequestData: UserCheckRequestData): Response<UserCheckResponseData> = api.checkAuthCode(userCheckRequestData)

    override suspend fun userRegister(userRegisterRequestData: UserRegisterRequestData): Response<UserResponseData> = api.userRegister(userRegisterRequestData)

    override fun saveRefreshToken(refreshToken: String) {
        sharedPref.setRefreshToken(refreshToken)
    }

    override fun saveAccessToken(accessToken: String) {
        sharedPref.setAccessToken(accessToken)
    }

    override fun getAccessToken(): String = sharedPref.getAccessToken()

    override fun savePhoneNumber(phoneNumber: String) {
        sharedPref.setPhoneNumber(phoneNumber)
    }

    override fun getPhoneNumber(): String = sharedPref.getPhoneNumber()

    override fun setUserRegistered(state: Boolean) {
        sharedPref.setUserRegistered(state)
    }

    override fun getUserRegistered(): Boolean = sharedPref.getUserRegistered()

    override suspend fun correctCode(): String = CORRECT_CODE

    companion object {
        const val CORRECT_CODE = "133337"
    }
}