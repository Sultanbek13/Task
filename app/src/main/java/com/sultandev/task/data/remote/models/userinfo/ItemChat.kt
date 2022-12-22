package com.sultandev.task.data.remote.models.userinfo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ItemChat(
    val id: Int,
    val name: String,
    val status: Boolean
): Parcelable