package com.example.finalproject.utils.extensions

import android.widget.TextView
import com.example.finalproject.R
import com.example.finalproject.utils.extensions.BaseExtensions.scaleTo2Symbols
import java.math.RoundingMode
import java.util.*

object TextViewExtensions {
    // Округление баланса до 2-х знаков и отображение в TextView
    fun TextView.setBalance(balance: Float) {
        val scaledBalance = balance.scaleTo2Symbols(RoundingMode.FLOOR)
        val result = String.format(Locale.ENGLISH, resources.getString(R.string.set_balance), scaledBalance)
        text = result
    }

    fun TextView.setErrorMessage(message: String?) {
        text = message ?: resources.getString(R.string.unknown_error)
    }

}