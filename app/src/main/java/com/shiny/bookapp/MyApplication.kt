package com.shiny.bookapp

import android.app.Application

class MyApplication: Application() {
    init {
        context = this
    }

    companion object {
        lateinit var context: Application
            private set
    }
}