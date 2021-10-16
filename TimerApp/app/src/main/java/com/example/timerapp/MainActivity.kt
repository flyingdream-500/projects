package com.example.timerapp

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
import com.example.timerapp.background.Background
import com.example.timerapp.background.ExecutorBackground
import com.example.timerapp.background.RxBackground
import com.example.timerapp.databinding.ActivityMainBinding
import com.example.timerapp.utils.Extensions.startText
import com.example.timerapp.utils.Extensions.stopText


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var counter = 0

    // timer started predicate
    private var started = false

    private lateinit var background: Background

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        background = RxBackground(
            ::started,
            ::stopped,
            ::update,
            ::endTimerMessage
        )
        background.initBackground(this)

        binding.apply {
            initCounter(timerInput.text)
            timerInput.setText(background.counter.toString())
            timerInput.addTextChangedListener { text ->
                initCounter(text!!)
            }
            timerButton.setOnClickListener {
                background.start(started)
            }
        }

        if (savedInstanceState != null) {
            started = savedInstanceState.getBoolean(STARTED_KEY)
            if (started) {
                started = false
                background.start(started)
            }
        }

    }

    private fun initCounter(text: Editable?) {
        val number = text.toString()
        counter = if (number.isNotEmpty() && number.isDigitsOnly()) number.toInt() else 0
        background.counter = counter
    }


    private fun update(counter: Int) {
        binding.timerInput.setText(counter.toString())
    }

    private fun started() {
        started = true
        binding.timerButton.stopText()
    }
    private fun stopped() {
        started = false
        binding.timerButton.startText()
    }

    override fun onPause() {
        super.onPause()
        background.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        background.close()
    }

    private fun endTimerMessage() {
        Toast.makeText(
            this,
            resources.getString(R.string.end_timer),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STARTED_KEY, started)
        stopped()
    }

    companion object {
        const val STARTED_KEY = "STARTED_KEY"
    }
}

