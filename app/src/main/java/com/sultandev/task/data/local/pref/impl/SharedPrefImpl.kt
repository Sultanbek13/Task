package com.sultandev.task.data.local.pref.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import com.sultandev.task.data.local.pref.SharedPref
import com.sultandev.task.data.remote.models.userinfo.ProfileData
import java.net.URI
import javax.inject.Inject

class SharedPrefImpl @Inject constructor(private val sharedPref: SharedPreferences) : SharedPref {

    private val editor = sharedPref.edit()
    private val gson = Gson()

    override fun setRefreshToken(refreshToken: String) {
        editor.putString(REFRESH_TOKEN, refreshToken).apply()
    }

    override fun getRefreshToken(): String = sharedPref.getString(REFRESH_TOKEN, "") ?: ""

    override fun setAccessToken(accessToken: String) {
        editor.putString(ACCESS_TOKEN, accessToken).apply()
    }

    override fun getAccessToken(): String = sharedPref.getString(ACCESS_TOKEN, "") ?: ""

    override fun setPhoneNumber(phoneNumber: String) {
        editor.putString(PHONE_NUMBER, phoneNumber).apply()
    }

    override fun getPhoneNumber(): String = sharedPref.getString(PHONE_NUMBER, "") ?: ""

    override fun getUserRegistered(): Boolean = sharedPref.getBoolean(USER_REGISTERED, false)

    override fun setUserRegistered(state: Boolean) {
        editor.putBoolean(USER_REGISTERED, state).apply()
    }

    override fun saveUserInfo(profileData: ProfileData) {
        val json = gson.toJson(profileData)
        editor.putString(PROFILE_DATA, json).apply()
    }

    override fun getUserInfo(): ProfileData {
        val json = sharedPref.getString(PROFILE_DATA, "")
        return gson.fromJson(json, ProfileData::class.java)
    }

    override fun saveUriAvatarProfile(uri: String) {
        editor.putString(URI_AVATAR, uri).apply()
    }

    override fun getUriAvatarProfile(): String = sharedPref.getString(URI_AVATAR, "") ?: ""

    override fun setUpdateProfile(state: Boolean) {
        editor.putBoolean(CHECK_UPDATE, state).apply()
    }

    override fun getUpdateProfile(): Boolean = sharedPref.getBoolean(CHECK_UPDATE, false)

    companion object {
        const val REFRESH_TOKEN = "refresh_token"
        const val ACCESS_TOKEN = "access_token"
        const val PHONE_NUMBER = "phone_number"
        const val USER_REGISTERED = "user_registered"
        const val PROFILE_DATA = "profile_data"
        const val URI_AVATAR = "uri_avatar"
        const val CHECK_UPDATE = "check_update"
    }
}