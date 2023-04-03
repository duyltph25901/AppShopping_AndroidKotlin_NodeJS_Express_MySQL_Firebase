package com.example.laptop_app.controller.views

import android.app.Application

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        DataLocalManagement.init(applicationContext)
    }
}