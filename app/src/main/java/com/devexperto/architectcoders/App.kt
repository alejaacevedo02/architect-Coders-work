package com.devexperto.architectcoders

import android.app.Application
import com.devexperto.architectcoders.di.initDi

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDi()
    }
}
