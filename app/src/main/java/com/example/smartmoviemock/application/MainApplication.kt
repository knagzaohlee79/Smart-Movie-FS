package com.example.smartmoviemock.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    companion object {
        private var _instance: MainApplication? = null
        val instance: MainApplication
            get() = _instance!!
    }

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }
}