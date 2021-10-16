package com.example.timerapp.background

import android.content.Context
import androidx.core.content.ContextCompat
import java.util.concurrent.*


class ExecutorBackground(
    private val startedUi: () -> Unit,
    private val stoppedUi: () -> Unit,
    private val updateUi: (Int) -> Unit,
    private val endTimerMessage: () -> Unit
) : Background {

    override var counter = 0

    private var backgroundExecutor: ScheduledExecutorService? = null
    private var baseExecutor: Executor? = null
    private var timerFuture: ScheduledFuture<*>? = null


    override fun initBackground(context: Context) {
        baseExecutor = ContextCompat.getMainExecutor(context)
        backgroundExecutor = Executors.newSingleThreadScheduledExecutor()
    }


    override fun start(started: Boolean) {
        if (!started && counter != 0) {
            startedUi.invoke()
            timerFuture = backgroundExecutor?.scheduleAtFixedRate(
                {
                    counter--
                    baseExecutor?.execute {
                        updateUi.invoke(counter)
                        if (counter == 0) {
                            stoppedUi.invoke()
                            endTimerMessage.invoke()
                            cancel()
                        }
                    }
                }, 1, 1, TimeUnit.SECONDS
            )
        } else {
            stoppedUi.invoke()
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