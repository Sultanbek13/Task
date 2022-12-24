package com.sultandev.task.data.local.pref

import android.net.Uri
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

    fun saveUriAvatarProfile(uri: String)

    fun getUriAvatarProfile(): String

    fun setUpdateProfile(state: Boolean)

    fun getUpdateProfile(): Boolean

}