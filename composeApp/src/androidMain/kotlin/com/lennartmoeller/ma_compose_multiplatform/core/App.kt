package com.lennartmoeller.ma_compose_multiplatform.core

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}
