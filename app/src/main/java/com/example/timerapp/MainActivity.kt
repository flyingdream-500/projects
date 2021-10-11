package com.example.timerapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
import com.example.timerapp.databinding.ActivityMainBinding
import com.example.timerapp.utils.Extensions.endTimerMessage
import com.example.timerapp.utils.Extensions.startText
import com.example.timerapp.utils.Extensions.stopText
import java.util.concurrent.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var counter = 0

    //is timer started predicate
    private var started = false

    // Main and Background executors
    private var backgroundExecutor: ScheduledExecutorService? = null
    private var baseExecutor: Executor? = null
    private var timerFuture: ScheduledFuture<*>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //executors
        baseExecutor = ContextCompat.getMainExecutor(this)
        backgroundExecutor = Executors.newSingleThreadScheduledExecutor()

        // Init Counter
        initCounter(binding.timerInput.text!!)
        binding.timerInput.setText(counter.toString())


        //Listeners
        binding.timerInput.addTextChangedListener { text ->
            initCounter(text!!)
        }
        binding.timerButton.setOnClickListener {
            timerStart()
        }

        if (savedInstanceState != null) {
            started = savedInstanceState.getBoolean(STARTED_KEY)
            if (started) {
                started = false
                timerStart()
            }
        }

    }

    private fun initCounter(text: Editable) {
        val number = text.toString()
        counter = if (number.isNotEmpty() && number.isDigitsOnly()) number.toInt() else 0
    }

    private fun timerStart() {
        if (!started && counter != 0) {
            started()
            timerFuture = backgroundExecutor?.scheduleAtFixedRate(
                {
                    --counter
                    baseExecutor?.execute { updateInputTimer() }
                }, 1, 1, TimeUnit.SECONDS
            )
        } else {
            stopped()
        }
    }

    private fun updateInputTimer() {
        binding.timerInput.setText(counter.toString())
        if (counter == 0) {
            stopped()
            endTimerMessage()
        }
    }



    private fun started() {
        started = true
        binding.timerButton.stopText()
    }

    private fun stopped() {
        started = false
        binding.timerButton.startText()
        timerFuture?.cancel(true)
    }

    override fun onPause() {
        super.onPause()
        timerFuture?.cancel(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        backgroundExecutor?.shutdownNow()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STARTED_KEY, started)
    }

    companion object {
        const val STARTED_KEY = "STARTED_KEY"
    }
}

