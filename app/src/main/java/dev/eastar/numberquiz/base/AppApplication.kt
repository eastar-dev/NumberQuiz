package dev.eastar.numberquiz.base

import android.app.Application
import android.log.logActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        logActivity()
    }
}