package com.example.contentproviderapp

import android.app.Application
import android.content.Context
import com.example.contentproviderapp.di.AppComponent
import com.example.contentproviderapp.di.DaggerAppComponent


class MainApp : Application() {

    lateinit var appComponent: AppComponent

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        appComponent = DaggerAppComponent.factory().create(this)
    }
}

/**
 * Расширение для обращения к компоненту из контекста( неважно какого)
 */
val Context.appComponent: AppComponent
    get() = when (this) {
        is MainApp -> appComponent
        else -> this.applicationContext.appComponent
    }