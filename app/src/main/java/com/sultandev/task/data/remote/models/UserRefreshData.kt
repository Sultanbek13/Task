package com.sultandev.task.data.remote.models

import com.google.gson.annotations.SerializedName

class UserRefreshData(
    @SerializedName("refresh_token")
    val refreshToken: String
)