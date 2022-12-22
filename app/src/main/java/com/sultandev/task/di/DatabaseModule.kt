package com.sultandev.task.di

import android.content.Context
import android.content.SharedPreferences
import com.sultandev.task.data.local.pref.SharedPref
import com.sultandev.task.data.local.pref.impl.SharedPrefImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @[Singleton Provides]
    fun provideSharedPreferences(@ApplicationContext ctx: Context): SharedPreferences =
        ctx.getSharedPreferences("app_data", Context.MODE_PRIVATE)

    @[Singleton Provides]
    fun provideSharedPref(sharedPref: SharedPreferences): SharedPref = SharedPrefImpl(sharedPref)

}