package com.lennartmoeller.ma.composemultiplatform.core

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}
