package com.sultandev.task.data.local.pref

import com.sultandev.task.data.remote.models.userinfo.ProfileData

interface SharedPref {

    fun setRefreshToken(refreshToken: String)

    fun getRefreshToken(): String

    fun setAccessToken(accessToken: String)

    fun getAccessToken(): String

    fun setPhoneNumber(phoneNumber: String)

    fun getPhoneNumber(): String

    fun getUserRegistered(): Boolean

    fun setUserRegistered(state: Boolean)

    fun saveUserInfo(profileData: ProfileData)

    fun getUserInfo(): ProfileData

}