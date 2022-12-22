package com.sultandev.task.data.remote.models

import com.google.gson.annotations.SerializedName

class UserResponseData(
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("user_id")
    val userId: String,
)