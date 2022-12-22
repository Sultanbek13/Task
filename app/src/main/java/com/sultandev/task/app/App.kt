package com.sultandev.task.app

import android.app.Application
import com.sultandev.task.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    companion object {
        private var instance: App? = null
        fun getInstanceApp() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        instance = this
    }
}