package com.google.mlkit.vision.demo

import android.app.Application
import com.google.mlkit.vision.demo.data.AppContainer
import com.google.mlkit.vision.demo.data.AppDataContainer

class RepsTrackerApplication: Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}