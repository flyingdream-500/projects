package com.example.timerapp.utils

import android.widget.Button
import com.example.timerapp.R

object Extensions {
    fun Button.startText() {
        text = resources.getString(R.string.start)
    }

    fun Button.stopText() {
        text = resources.getString(R.string.stop)
    }
}