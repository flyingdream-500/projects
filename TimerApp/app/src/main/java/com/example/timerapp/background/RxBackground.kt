package com.example.timerapp.background

import android.content.Context
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

class RxBackground(
    private val startedUi: () -> Unit,
    private val stoppedUi: () -> Unit,
    private val updateUi: (Int) -> Unit,
    private val endTimerMessage: () -> Unit
): Background {

    override var counter = 0

    private var disposable: Disposable? = null
    private var observable: Observable<Int>? = null
    private var observer: Observer<Int>? = null


    override fun initBackground(context: Context) {
        observable = getObservable()
        observer = getObserver()
    }

    override fun start(started: Boolean) {
        if (!started && counter != 0) {
            startedUi.invoke()
            observable?.subscribe(observer!!)
        } else {
            stoppedUi.invoke()
            cancel()
        }
    }

    override fun cancel() {
        disposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

    override fun close() {
        disposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

    /**
     * Оператор interval для работы использует Schedulers.computation(),
     * поэтому не используем subscribeOn()
     */
    private fun getObservable(): Observable<Int> {
        return Observable.interval(1, TimeUnit.SECONDS)
            .map { counter.dec() }
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getObserver(): Observer<Int> {
        return object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onNext(t: Int) {
                updateUi.invoke(t)
                if (counter == 0) {
                    stoppedUi.invoke()
                    endTimerMessage.invoke()
                    cancel()
                }
            }

            override fun onError(e: Throwable) {}

            override fun onComplete() {}

        }
    }
}