package com.example.timerapp.utils

import android.content.Context
import android.widget.Button
import android.widget.Toast
import com.example.timerapp.R

object Extensions {
    fun Button.startText() {
        text = resources.getString(R.string.start)
    }

    fun Button.stopText() {
        text = resources.getString(R.string.stop)
    }

    fun Context.endTimerMessage() {
        Toast.makeText(
            this,
            resources.getString(R.string.end_timer),
            Toast.LENGTH_SHORT
        ).show()
    }
}