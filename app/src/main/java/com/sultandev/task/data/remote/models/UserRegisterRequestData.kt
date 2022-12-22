package com.sultandev.task.data.remote.models

import com.google.gson.annotations.SerializedName

class UserRegisterRequestData(
    @SerializedName("phone")
    val phoneNumber: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val userName: String
)