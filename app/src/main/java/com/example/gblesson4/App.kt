package com.example.gblesson4

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        privateContext = this
    }

    companion object {
        private var privateContext: App? = null
        val appContext get() = privateContext!!
    }
}