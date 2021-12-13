package com.example.voicerecorderapp.presentation

import android.app.Application
import android.content.Context
import com.example.voicerecorderapp.di.AppComponent
import com.example.voicerecorderapp.di.DaggerAppComponent


class MainApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}

/**
 * Расширение для обращения к компоненту из контекста( неважно какого)
 */
val Context.appComponent: AppComponent
    get() = when(this) {
        is MainApp -> appComponent
        else -> this.applicationContext.appComponent
    }