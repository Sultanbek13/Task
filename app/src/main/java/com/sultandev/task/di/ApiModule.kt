package com.sultandev.task.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.sultandev.task.data.remote.api.AuthApi
import com.sultandev.task.data.remote.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @[Provides Singleton]
    fun provideBaseUrl(): String = "https://plannerok.ru"

    @[Provides Singleton]
    fun provideClient(@ApplicationContext ctx: Context): OkHttpClient =
        OkHttpClient.Builder()
        .addInterceptor(
            ChuckerInterceptor.Builder(ctx)
                .build()
        ).build()

    @[Provides Singleton]
    fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @[Provides Singleton]
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @[Provides Singleton]
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

   /* @Provides
    @Singleton
    fun provideOkHttpClient(sharedPref: SharedPref): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(
            OAuthInterceptor(
                sharedPref
            )
        )
            .addInterceptor(interceptor)
            .build()
    }*/
}