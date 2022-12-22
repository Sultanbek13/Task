package com.sultandev.task.utils

import android.content.Context
import android.widget.Toast

fun toast(ctx: Context, str: String) = Toast.makeText(ctx, str, Toast.LENGTH_SHORT).show()