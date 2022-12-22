package com.sultandev.task.data.remote.oauth

import com.sultandev.task.data.local.pref.SharedPref
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OAuthInterceptor @Inject constructor(
    private val sharedPref: SharedPref
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        sharedPref.getAccessToken().let {
            requestBuilder.addHeader("Authorization","Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }
}