package com.example.timerapp

import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
import com.example.timerapp.databinding.ActivityMainBinding
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var counter = 0
    //is timer started predicate
    private var started = false

    // Main and Background executors
    private var backgroundExecutor: ScheduledExecutorService? = null
    private var baseExecutor: Executor?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        baseExecutor = ContextCompat.getMainExecutor(this)

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

    }

    private fun initCounter(text: Editable) {
        val number = text.toString()
        counter = if (number.isNotEmpty() && number.isDigitsOnly()) number.toInt() else 0
    }

    private fun timerStart() {
        if(!started && counter != 0) {
            backgroundExecutor = Executors.newSingleThreadScheduledExecutor()
            started = true
            binding.timerButton.stopText()
            backgroundExecutor?.scheduleAtFixedRate({
                counter = backgroundExecutor?.minusOneSeconds() ?: 0
                baseExecutor?.execute {
                    updateInputTimer()
                }
            }, 1, 1, TimeUnit.SECONDS)
        } else {
            started = false
            binding.timerButton.startText()
            backgroundExecutor?.shutdownNow()
        }
    }

    private fun updateInputTimer() {
        binding.timerInput.setText(counter.toString())
        if (counter == 0) {
            binding.timerButton.startText()
            Toast.makeText(binding.root.context, resources.getString(R.string.end_timer), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COUNTER_KEY, counter)
        outState.putBoolean(STARTED_KEY, started)
        backgroundExecutor?.shutdownNow()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        counter = savedInstanceState.getInt(COUNTER_KEY)
        binding.timerInput.setText(counter.toString())
        started = savedInstanceState.getBoolean(STARTED_KEY)
        if (started) {
            started = false
            timerStart()
        }
    }

    private fun ScheduledExecutorService.minusOneSeconds(): Int {
        counter = counter.dec()
        return if (counter > 0) {
            counter
        } else {
            started = false
            shutdownNow()
            0
        }
    }

    private fun Button.startText() {
        text = resources.getString(R.string.start)
    }

    private fun Button.stopText() {
        text = resources.getString(R.string.stop)
    }

    companion object {
        const val COUNTER_KEY = "COUNTER_KEY"
        const val STARTED_KEY = "STARTED_KEY"
    }
}

