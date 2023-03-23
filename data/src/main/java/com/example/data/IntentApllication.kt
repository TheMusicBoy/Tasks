package com.example.data

import android.app.Application

class IntentApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        TaskRepository.init(this)
    }

}