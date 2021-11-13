package com.example.voicerecorderapp.service.background

/**
 * Интерфейс фонового таймера
 */
interface IBackgroundTimer {
    var counter: Long

    fun start(started: Boolean)
    fun cancel()
    fun close()
}