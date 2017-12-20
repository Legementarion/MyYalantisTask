package com.lego.myyalantistask

import android.app.Application
import com.lego.myyalantistask.di.AppComponent
import com.lego.myyalantistask.di.BaseModule
import com.lego.myyalantistask.di.DaggerAppComponent

class MainApp : Application() {

    companion object {
        lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        graph = DaggerAppComponent.builder().baseModule(BaseModule(applicationContext)).build()
    }
}