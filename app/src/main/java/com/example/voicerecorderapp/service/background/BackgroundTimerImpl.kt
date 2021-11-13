package com.example.voicerecorderapp.service.background

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit


/**
 * Реализация таймера в отдельном потоке
 * @param updateUi - обновление ui через работу Мессенджера в сервисе [com.example.voicerecorderapp.service.RecordingService]
 */
class BackgroundTimerImpl(
    private val updateUi: (Long) -> Unit
) : IBackgroundTimer {

    override var counter: Long = 0L

    private var backgroundExecutor: ScheduledExecutorService? = Executors.newSingleThreadScheduledExecutor()
    private var timerFuture: ScheduledFuture<*>? = null

    override fun start(started: Boolean) {
        if (started) {
            timerFuture = backgroundExecutor?.scheduleAtFixedRate(
                {
                    counter++
                    updateUi.invoke(counter)
                }, 1, 1, TimeUnit.SECONDS
            )
        } else {
            cancel()
        }
    }

    override fun cancel() {
        timerFuture?.cancel(true)
    }

    override fun close() {
        backgroundExecutor?.shutdownNow()
    }

}