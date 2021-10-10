package com.example.timerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.timerapp.databinding.ActivityMainBinding
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var counter = 0
    private var started = false

    private var backgroundExecutor: ScheduledExecutorService? = null
    private var baseExecutor: Executor?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        baseExecutor = ContextCompat.getMainExecutor(this)


        counter = binding.timerInput.text.toString().toInt()
        binding.timerInput.addTextChangedListener { text ->
            val number = text.toString()
            counter = if (number.isEmpty()) 0 else number.toInt()
        }


        binding.timerStart.setOnClickListener {
            timerStart()
        }


    }

    private fun timerStart() {
        if(!started) {
            backgroundExecutor = Executors.newSingleThreadScheduledExecutor()
            started = true
            backgroundExecutor?.scheduleAtFixedRate({
                counter = backgroundExecutor?.minusOneSeconds() ?: 0
                baseExecutor?.execute {
                    binding.timerInput.setText(counter.toString())
                    if (counter == 0) {
                        Toast.makeText(binding.root.context, "Дилинь - дилинь ёпт", Toast.LENGTH_SHORT).show()
                    }
                }
            }, 1, 1, TimeUnit.SECONDS)
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("TAGG", "onPause")
//        started = false
//        backgroundExecutor?.shutdownNow()
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAGG", "onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COUNTER_KEY, counter)
        outState.putBoolean(STARTED_KEY, started)
        backgroundExecutor?.shutdownNow()
        Log.d("TAGG", "onSaveInstanceState: counter: $counter ")
        Log.d("TAGG", "onSaveInstanceState: started: $started ")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        counter = savedInstanceState.getInt(COUNTER_KEY)
        binding.timerInput.setText(counter.toString())
        started = savedInstanceState.getBoolean(STARTED_KEY)
        Log.d("TAGG", "counter: $counter started: $started")
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
            shutdown()
            0
        }
    }

    companion object {
        const val COUNTER_KEY = "COUNTER_KEY"
        const val STARTED_KEY = "STARTED_KEY"
    }
}