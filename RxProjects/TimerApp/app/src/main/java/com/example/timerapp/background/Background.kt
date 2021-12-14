package com.example.timerapp.background

import android.content.Context

interface Background {
    var counter: Int

    fun initBackground(context: Context)
    fun start(started: Boolean)
    fun cancel()
    fun close()
}