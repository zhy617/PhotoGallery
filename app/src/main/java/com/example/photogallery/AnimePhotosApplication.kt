package com.example.photogallery

import android.app.Application
import com.example.photogallery.data.AppContainer
import com.example.photogallery.data.DefaultAppContainer

class AnimePhotosApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}

