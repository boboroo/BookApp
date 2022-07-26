package com.shiny.bookapp

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class MyApplication: Application() {
    init {
        context = this
    }

    companion object {
        lateinit var context: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()

        Logger.addLogAdapter(AndroidLogAdapter())
    }
}